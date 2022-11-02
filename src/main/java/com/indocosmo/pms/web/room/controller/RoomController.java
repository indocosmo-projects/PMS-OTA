package com.indocosmo.pms.web.room.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.indocosmo.pms.enumerator.RoomHkStatus;
import com.indocosmo.pms.enumerator.RoomInventoryStatus;
import com.indocosmo.pms.enumerator.RoomOccupancyStatus;
import com.indocosmo.pms.web.common.Encryption;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.department.model.Department;
import com.indocosmo.pms.web.dto.JqGridListWrapperDTO;
import com.indocosmo.pms.web.exception.CustomException;
import com.indocosmo.pms.web.floor.model.Floor;
import com.indocosmo.pms.web.room.model.Room;
import com.indocosmo.pms.web.room.model.RoomFeature;
import com.indocosmo.pms.web.room.service.RoomService;
import com.indocosmo.pms.web.roomType.service.RoomTypeService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping("/room")
public class RoomController {

	@Autowired
	private RoomService roomService;

	@Autowired
	private RoomTypeService roomTypeService;

	public static final Logger logger = LoggerFactory.getLogger(RoomController.class);

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	private SysPermissions permissionObj;

	/**
	 * Room module save and update method
	 * 
	 * @param room
	 * @param newRecord
	 * @param session
	 * @return if record added successfully, page redirected to rool list page
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public String save(@ModelAttribute Room room,
			@RequestParam(value = "newRecord", required = true, defaultValue = "true") String newRecord,
			HttpSession session) throws Exception {
		boolean newRec = newRecord.equals("true") ? true : false;

		boolean isSave = roomService.save(room, newRec);

		if (isSave) {
			Map<String, String> temp1 = new HashMap<String, String>();
			Map<String, String> temp2 = new HashMap<String, String>();
			int count = roomService.getCount(temp1, temp2);
			session.setAttribute("recordCount", count);

			return "redirect:/room/roomList";
		} else {
			return "room/room_edit";
		}
	}

	/**
	 * RoomList function
	 * 
	 * @param room
	 * @param currentPage
	 * @param rowLimit
	 * @param pagingStart
	 * @param advanceSearch
	 * @param sortVal
	 * @param simpleSearch
	 * @param session
	 * @return jsonData
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JqGridListWrapperDTO list(@ModelAttribute("room") Room room,
			@RequestParam(value = "currentPage", required = false) String currentPage,
			@RequestParam(value = "rowLimit", required = false) String rowLimit,
			@RequestParam(value = "pagingStart", required = false) String pagingStart,
			@RequestParam(value = "advanceSearch", required = false) String advanceSearch,
			@RequestParam(value = "sortVal", required = false) String sortVal,
			@RequestParam(value = "simpleSearh", required = false) String simpleSearch, HttpSession session)
			throws Exception {
		Map<String, String> simpleSearchMap = new HashMap<String, String>();

		if (simpleSearch != null && !simpleSearch.equals("")) {
			simpleSearchMap.put("number", simpleSearch);
			simpleSearchMap.put("name", simpleSearch);
		}

		Map<String, String> advanceSearchMap = new HashMap<String, String>();

		if (advanceSearch != null && !advanceSearch.equals("null") && !advanceSearch.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				advanceSearchMap = objectMapper.readValue(advanceSearch, new TypeReference<HashMap<String, String>>() {
				});
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Method : list, " + Throwables.getStackTraceAsString(e));
				throw new CustomException();
			}
		}

		if (currentPage.equals("")) {
			currentPage = "1";
		}

		if (pagingStart.equals("NaN")) {
			pagingStart = "1";
		}

		JqGridListWrapperDTO jqGridListWrapperDTO = null;
		jqGridListWrapperDTO = roomService.list(currentPage, rowLimit, pagingStart, advanceSearchMap, sortVal,
				simpleSearchMap);

		session.removeAttribute("recordCount");

		return jqGridListWrapperDTO;
	}

	/**
	 * Delete a record from room module(soft deletion)
	 * 
	 * @param roomId
	 * @param session
	 * @return redirect to list page if the deletion is successful.
	 * @throws Exception
	 */
	@RequestMapping(value = "delete")
	public @ResponseBody boolean delete(@RequestParam(value = "ids") String id, HttpSession session) throws Exception {
		boolean isDeleted = roomService.delete(Integer.parseInt(id));
		session.removeAttribute("recordCount");

		Map<String, String> temp1 = new HashMap<String, String>();
		Map<String, String> temp2 = new HashMap<String, String>();
		int count = roomService.getCount(temp1, temp2);
		session.setAttribute("recordCount", count);

		return isDeleted;
	}

	/**
	 * Redirect to room list page
	 * 
	 * @param model
	 * @return url to the room list page
	 */
	@RequestMapping(value = "roomList", method = RequestMethod.GET)
	public String listRedirect(HttpSession session, @ModelAttribute Department department, Model model)
			throws Exception {
		Map<Integer, String> roomTypes = roomTypeService.getRoomTypeList();
		model.addAttribute("roomTypes", roomTypes);
		List<Floor> floorList = roomService.getFloor();
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (Floor i : floorList) {
			map.put(i.getId(), i.getCode());
		}
		model.addAttribute("floorList", map);
		permissionObj = pageAccessPermissionService.getPermission(session, "MST_ROOM");
		model.addAttribute("curPagePerObj", permissionObj);

		return ((permissionObj.isCanView() && permissionObj.isIs_view_applicable()) ? "room/room_list"
				: "access_denied/access_denied");

	}

	/**
	 * redirect to room edit page
	 * 
	 * @param room
	 * @param model
	 * @return url of room edit page
	 * @throws Exception
	 */
	@RequestMapping(value = "roomRedirect", method = RequestMethod.GET)
	public String editRedirect(@ModelAttribute Room room, Model model) throws Exception {
		Map<Integer, String> roomTypes = roomTypeService.getRoomTypeList();
		List<RoomFeature> roomFeatureList = roomService.getRoomFeatures();

		model.addAttribute("roomTypes", roomTypes);

		List<Floor> floorList = roomService.getFloor();
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (Floor i : floorList) {
			map.put(i.getId(), i.getCode());
		}
		model.addAttribute("floorList", map);

		model.addAttribute("invStatus", RoomInventoryStatus.values());
		model.addAttribute("hkStatus", RoomHkStatus.values());
		model.addAttribute("occStatus", RoomOccupancyStatus.values());
		model.addAttribute("roomFeatures", roomFeatureList);
		model.addAttribute("room", room);

		return "room/room_edit";
	}

	/**
	 * redirect to room edit page(edit mode)
	 * 
	 * @param room
	 * @param model
	 * @param ids
	 * @return url of room edit page
	 * @throws Exception
	 */
	@RequestMapping(value = "getRecord", method = RequestMethod.GET)
	public String getRecord(@ModelAttribute Room room, Model model,
			@RequestParam(value = "ids", required = true) String id, HttpSession session) throws Exception {
		try {
			permissionObj = pageAccessPermissionService.getPermission(session, "MST_ROOM");
			model.addAttribute("curPagePerObj", permissionObj);
			Encryption encryption = new Encryption();
			//int roomId = Integer.parseInt(encryption.decrypt(id));
			String roomId = encryption.decrypt(id);
			room = roomService.getRecord(roomId);
			String value = encryption.encrypt((room.getNumber()));
			room.setEncryption(value);

			List<Floor> floorList = roomService.getFloor();
			Map<Integer, String> map = new HashMap<Integer, String>();
			for (Floor i : floorList) {
				map.put(i.getId(), i.getCode());
			}
			model.addAttribute("floorList", map);

			Map<Integer, String> roomTypes = roomTypeService.getRoomTypeList();
			List<RoomFeature> roomFeatureList = roomService.getRoomFeatures();

			model.addAttribute("roomTypes", roomTypes);
			model.addAttribute("invStatus", RoomInventoryStatus.values());
			model.addAttribute("hkStatus", RoomHkStatus.values());
			model.addAttribute("occStatus", RoomOccupancyStatus.values());
			model.addAttribute("roomFeatures", roomFeatureList);
			model.addAttribute("room", room);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Method : getRecord, " + Throwables.getStackTraceAsString(e));
			throw new CustomException();
		}

		return "room/room_edit";
	}

	/**
	 * check whether the room number existing or not
	 * 
	 * @param roomNo
	 * @return false if the room number is not existing. Else true
	 * @throws Exception
	 */
	@RequestMapping(value = "roomExist", method = RequestMethod.GET)
	public @ResponseBody boolean roomNumExist(@RequestParam(value = "code") String roomNo) throws Exception {
		boolean isExist = roomService.roomNumExist(roomNo);

		return isExist;
	}

	/**
	 * 
	 * @param simpleSearchData
	 * @param advanceSearch
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getCount", method = RequestMethod.POST)
	public @ResponseBody String totalCount(@RequestParam(value = "simpleSearch") String simpleSearchData,
			@RequestParam(value = "advanceSearch") String advanceSearch) throws Exception {
		Map<String, String> simpleSearchMap = new HashMap<String, String>();

		if (simpleSearchData != null && !simpleSearchData.equals("")) {
			simpleSearchMap.put("number", simpleSearchData);
			simpleSearchMap.put("name", simpleSearchData);
		}

		Map<String, String> advanceSearchMap = new HashMap<String, String>();

		if (advanceSearch != null && !advanceSearch.equals("null") && !advanceSearch.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				advanceSearchMap = objectMapper.readValue(advanceSearch, new TypeReference<HashMap<String, String>>() {
				});
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Method : getCount, " + Throwables.getStackTraceAsString(e));
				throw new CustomException();
			}
		}

		int count = roomService.getCount(advanceSearchMap, simpleSearchMap);

		return Integer.toString(count);
	}

	/**
	 * Add new feature to room_feature table
	 * 
	 * @param roomFeature
	 * @return "saved" if successfully saved. "exist" if the room feature exist
	 * @throws Exception
	 */
	@RequestMapping(value = "addFeature", method = RequestMethod.GET)
	public @ResponseBody String addRoomFeature(@RequestParam(value = "id") String id,
			@RequestParam(value = "roomFeature") String roomFeature) throws Exception {

		String status = "";
		RoomFeature feature = new RoomFeature();
		feature.setFeature(roomFeature);

		if (id != null && !id.equals("")) {
			feature.setId(Integer.parseInt(id));
		}

		if (!roomService.featureExist(feature)) {
			RoomFeature rmFeature = new RoomFeature();
			rmFeature.setId(Integer.parseInt(id));
			rmFeature.setFeature(roomFeature);
			roomService.addFeature(rmFeature);

			List<RoomFeature> roomFeatures = roomService.getRoomFeatures();
			ObjectMapper objectMapper = new ObjectMapper();
			status = objectMapper.writeValueAsString(roomFeatures);
		} else {
			status = "exist";
		}

		return status;
	}

	@RequestMapping(value = "getRoomFeatures", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<RoomFeature> getRoomFeatures() throws Exception {
		List<RoomFeature> roomFeatures = roomService.getRoomFeatures();

		return roomFeatures;
	}

	@RequestMapping(value = "deleteRoomFeature", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<RoomFeature> deleteRoomFeature(@RequestParam(value = "id") String id) throws Exception {
		roomService.deleteFeature(id);
		List<RoomFeature> roomFeatures = roomService.getRoomFeatures();

		return roomFeatures;
	}

	@RequestMapping(value = "getFloor", method = RequestMethod.GET)
	public @ResponseBody String getRoomTypes(HttpSession session) throws Exception {
		List<Floor> floorList = roomService.getFloor();
		String jsonlist = new Gson().toJson(floorList);
		return jsonlist;
	}
}