package com.indocosmo.pms.web.roomType.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.indocosmo.pms.web.common.PageAccessPermissionService;
import com.indocosmo.pms.web.roomType.model.RoomType;
import com.indocosmo.pms.web.roomType.service.RoomTypeService;
import com.indocosmo.pms.web.syetemDefPermission.model.SysPermissions;

@Controller
@RequestMapping("/roomType")
public class RoomTypeController {

	public static final String SYDEF_PERMISION_DENIED_PAGE_URL = "access_denied/access_denied";

	@Autowired
	RoomTypeService roomTypeService;

	@Autowired
	private PageAccessPermissionService pageAccessPermissionService;

	private SysPermissions permissionObj;

	List<String> fileNames = new ArrayList<String>();

	@RequestMapping(value = "/roomTypeList", method = RequestMethod.GET)
	public String list(Model model, HttpSession session) throws Exception {
		String pageUrl = "/room_type/room_type_list";
		permissionObj = pageAccessPermissionService.getPermission(session, "MST_ROM_TYP");
		if (permissionObj.isCanView() && permissionObj.isIs_view_applicable()) {
			model.addAttribute("curPagePerObj", permissionObj);
			model.addAttribute("imageDirectory", System.getProperty("catalina.home"));
		} else {
			pageUrl = SYDEF_PERMISION_DENIED_PAGE_URL;
		}
		return pageUrl;

	}

	@RequestMapping(value = "/roomTypeDetails", method = RequestMethod.POST)
	public @ResponseBody String getRoomTypes() {
		String jsonlist = null;
		try {
			List<RoomType> roomTypeList = roomTypeService.getRoomTypeLists();
			jsonlist = new Gson().toJson(roomTypeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonlist;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String delete(@RequestParam(value = "id") int id, HttpSession session) {
		int canDelete = roomTypeService.canDelete(id);
		String deleteStatus = "success";
		Gson g = new Gson();
		if (canDelete == 0) {
			boolean isDelete = roomTypeService.delete(id);
			if (isDelete) {
				deleteStatus = "status:" + deleteStatus;
			} else {
				deleteStatus = "status:error";
			}
		}
		return g.toJson(deleteStatus).toString();

	}

	@RequestMapping(value = "/codeExists", method = RequestMethod.POST)
	public @ResponseBody boolean isCodeExists(@RequestParam(value = "code") String code, HttpSession session)
			throws Exception {
		return roomTypeService.isCodeExists(code);

	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(HttpSession session, MultipartHttpServletRequest request,
			@RequestParam(value = "file1", required = false) MultipartFile file1,
			@RequestParam(value = "file2", required = false) MultipartFile file2,
			@RequestParam(value = "file3", required = false) MultipartFile file3,
			@RequestParam(value = "file4", required = false) MultipartFile file4,
			@RequestParam("roomTypeJson") String roomTypeJson) throws Exception {
		JsonParser parser = new JsonParser();
		JsonObject jobj = (JsonObject) parser.parse(roomTypeJson);
		String code = jobj.get("code").getAsString();
		String rootPath = System.getProperty("catalina.home");
		String absolutePath = "/pms_uploads/roomtypes/";
		String rootDirectory = rootPath + "/webapps/" + absolutePath;
		boolean isSave = false;
		RoomType rType = new RoomType();
		try {
			File dir = null;
			if (file1 != null || file2 != null || file3 != null || file4 != null) {
				dir = new File(rootDirectory);
				if (!dir.exists()) {
					dir.mkdirs();
				}
			}
			if (file1 != null) {
				File serverFile = new File(dir.getAbsolutePath() + File.separator + code + "_img1.jpg");
				file1.transferTo(serverFile);
				rType.setImage1(absolutePath + code + "_img1.jpg");
			}
			if (file2 != null) {
				File serverFile = new File(dir.getAbsolutePath() + File.separator + code + "_img2.jpg");
				file2.transferTo(serverFile);
				rType.setImage2(absolutePath + code + "_img2.jpg");
			}
			if (file3 != null) {
				File serverFile = new File(dir.getAbsolutePath() + File.separator + code + "_img3.jpg");
				file3.transferTo(serverFile);
				rType.setImage3(absolutePath + code + "_img3.jpg");
			}
			if (file4 != null) {
				File serverFile = new File(dir.getAbsolutePath() + File.separator + code + "_img4.jpg");
				file4.transferTo(serverFile);
				rType.setImage4(absolutePath + code + "_img4.jpg");
			}

			rType.setCode(jobj.get("code").getAsString());
			rType.setName(jobj.get("name").getAsString());
			rType.setDescription(jobj.get("description").getAsString());
			rType.setOverBookingPrcntg(jobj.get("overBookingPrcntg").getAsByte());
			rType.setSupportSingleOcc(jobj.get("supportSingleOcc").getAsBoolean());
			rType.setSupportDoubleOcc(jobj.get("supportDoubleOcc").getAsBoolean());
			rType.setSupportTripleOcc(jobj.get("supportTripleOcc").getAsBoolean());
			rType.setSupportQuadOcc(jobj.get("supportQuadOcc").getAsBoolean());
			rType.setDisplayOrder(jobj.get("displayOrder").getAsInt());
			isSave = roomTypeService.save(rType);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Gson g = new Gson();

		String saveStatus = "success";

		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";
		}
		return g.toJson(saveStatus).toString();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(MultipartHttpServletRequest request,
			@RequestParam(value = "file1", required = false) MultipartFile file1,
			@RequestParam(value = "file2", required = false) MultipartFile file2,
			@RequestParam(value = "file3", required = false) MultipartFile file3,
			@RequestParam(value = "file4", required = false) MultipartFile file4,

			@RequestParam(value = "roomTypeJson") String roomTypeJson, HttpSession session) throws Exception {
		Gson g = new Gson();
		boolean isSave = false;
		JsonParser parser = new JsonParser();
		JsonObject jobj = (JsonObject) parser.parse(roomTypeJson);

		if (file1 != null || file2 != null || file3 != null || file4 != null) {

			String code = jobj.get("code").getAsString();
			String rootPath = System.getProperty("catalina.home");
			String absolutePath = "/pms_uploads/roomtypes/";
			String rootDirectory = rootPath + "/webapps/" + absolutePath;
			File dir = new File(rootDirectory);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			RoomType newrType = new RoomType();
			try {
				if (file1 != null) {
					File serverFile = new File(dir.getAbsolutePath() + File.separator + code + "_img1.jpg");
					file1.transferTo(serverFile);
					newrType.setImage1(absolutePath + code + "_img1.jpg");
				} else if (!jobj.get("image1").isJsonNull()) {
					String img1 = jobj.get("image1").getAsString();
					if (img1 != null) {
						newrType.setImage1(img1);
					}
				} else if (jobj.get("img1Deleted").getAsBoolean()) {
					newrType.setImage1(null);
				}
				if (file2 != null) {
					File serverFile = new File(dir.getAbsolutePath() + File.separator + code + "_img2.jpg");
					file2.transferTo(serverFile);
					newrType.setImage2(absolutePath + code + "_img2.jpg");
				} else if (!jobj.get("image2").isJsonNull()) {
					String img2 = jobj.get("image2").getAsString();
					if (img2 != null) {
						newrType.setImage2(img2);
					}
				} else if (jobj.get("img2Deleted").getAsBoolean()) {
					newrType.setImage2(null);
				}

				if (file3 != null) {
					File serverFile = new File(dir.getAbsolutePath() + File.separator + code + "_img3.jpg");
					file3.transferTo(serverFile);
					newrType.setImage3(absolutePath + code + "_img3.jpg");
				} else if (!jobj.get("image3").isJsonNull()) {
					String img3 = jobj.get("image3").getAsString();
					if (img3 != null) {
						newrType.setImage3(img3);
					}
				} else if (jobj.get("img3Deleted").getAsBoolean()) {
					newrType.setImage3(null);
				}
				if (file4 != null) {
					File serverFile = new File(dir.getAbsolutePath() + File.separator + code + "_img4.jpg");
					file4.transferTo(serverFile);
					newrType.setImage4(absolutePath + code + "_img4.jpg");
				} else if (!jobj.get("image4").isJsonNull()) {
					String img4 = jobj.get("image4").getAsString();
					if (img4 != null) {
						newrType.setImage4(img4);
					}
				} else if (jobj.get("img4Deleted").getAsBoolean()) {
					newrType.setImage4(null);
				}

				newrType.setCode(jobj.get("code").getAsString());
				newrType.setName(jobj.get("name").getAsString());
				newrType.setDescription(jobj.get("description").getAsString());
				newrType.setOverBookingPrcntg(jobj.get("overBookingPrcntg").getAsByte());
				newrType.setSupportSingleOcc(jobj.get("supportSingleOcc").getAsBoolean());
				newrType.setSupportDoubleOcc(jobj.get("supportDoubleOcc").getAsBoolean());
				newrType.setSupportTripleOcc(jobj.get("supportTripleOcc").getAsBoolean());
				newrType.setSupportQuadOcc(jobj.get("supportQuadOcc").getAsBoolean());
				newrType.setDisplayOrder(jobj.get("displayOrder").getAsInt());
				newrType.setId(jobj.get("id").getAsInt());
				isSave = roomTypeService.save(newrType);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException npe) {
				npe.printStackTrace();
			}

		} else if (jobj.get("img1Deleted").getAsBoolean() || jobj.get("img2Deleted").getAsBoolean()
				|| jobj.get("img3Deleted").getAsBoolean() || jobj.get("img4Deleted").getAsBoolean()) {
			RoomType rmType = new RoomType();
			try {
				if (jobj.get("img1Deleted").getAsBoolean()) {
					rmType.setImage1(null);
				} else if (!jobj.get("image1").isJsonNull()) {
					String img1 = jobj.get("image1").getAsString();
					if (img1 != null) {
						rmType.setImage1(img1);
					}
				} else {
					rmType.setImage1(null);
				}

				if (jobj.get("img2Deleted").getAsBoolean()) {
					rmType.setImage2(null);
				} else if (!jobj.get("image2").isJsonNull()) {
					String img2 = jobj.get("image2").getAsString();
					if (img2 != null) {
						rmType.setImage2(img2);
					}

				} else {
					rmType.setImage2(null);
				}

				if (jobj.get("img3Deleted").getAsBoolean()) {
					rmType.setImage3(null);
				} else if (!jobj.get("image3").isJsonNull()) {
					String img3 = jobj.get("image3").getAsString();
					if (img3 != null) {
						rmType.setImage3(img3);
					}
				} else {
					rmType.setImage3(null);
				}

				if (jobj.get("img4Deleted").getAsBoolean()) {
					rmType.setImage4(null);
				} else if (!jobj.get("image4").isJsonNull()) {
					String img4 = jobj.get("image4").getAsString();
					if (img4 != null) {
						rmType.setImage4(img4);
					}
				} else {
					rmType.setImage4(null);
				}
				rmType.setCode(jobj.get("code").getAsString());
				rmType.setName(jobj.get("name").getAsString());
				rmType.setDescription(jobj.get("description").getAsString());
				rmType.setOverBookingPrcntg(jobj.get("overBookingPrcntg").getAsByte());
				rmType.setSupportSingleOcc(jobj.get("supportSingleOcc").getAsBoolean());
				rmType.setSupportDoubleOcc(jobj.get("supportDoubleOcc").getAsBoolean());
				rmType.setSupportTripleOcc(jobj.get("supportTripleOcc").getAsBoolean());
				rmType.setSupportQuadOcc(jobj.get("supportQuadOcc").getAsBoolean());
				rmType.setDisplayOrder(jobj.get("displayOrder").getAsInt());
				rmType.setId(jobj.get("id").getAsInt());
				isSave = roomTypeService.save(rmType);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			RoomType rType = new RoomType();
			rType = g.fromJson(roomTypeJson, RoomType.class);
			isSave = roomTypeService.save(rType);
		}
		String saveStatus = "success";

		if (isSave) {
			saveStatus = "status:" + saveStatus;
		} else {
			saveStatus = "status:error";

		}

		return g.toJson(saveStatus).toString();
	}

	@RequestMapping(value = "getAvailRooms", method = RequestMethod.POST)
	public @ResponseBody String availRooms(@RequestParam("id") int id, HttpSession session) throws Exception {
		Gson gson = new Gson();
		RoomType roomType = roomTypeService.getAvailRooms(id);
		return gson.toJson(roomType).toString();

	}

	@RequestMapping(value = "getRoomTypes", method = RequestMethod.GET)
	public @ResponseBody String getRoomTypes(HttpSession session) throws Exception {
		List<RoomType> roomTypeList = roomTypeService.getRoomTypeLists();
		String jsonlist = new Gson().toJson(roomTypeList);
		return jsonlist;
	}

}
