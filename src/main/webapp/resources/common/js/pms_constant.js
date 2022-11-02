
/**
 * SAVE_ACTION cookie values are using in jqGrid list for identifying the master edit page actions
 * 
 */
/*when user insert a new record in master edit page the application create cookie and save this value */
const SAVE_ACTION_INS="saveactionins";
/*when user update a record in master edit page the application create cookie and save this value */
const SAVE_ACTION_UPD="saveactionupd";

/**
 * if we want to increase the pagination no add corresponding no to const PAGING values
 * if we want to decrease the pagination no less corresponding no to const PAGING values
 * eg:- pagination no is 6
 *      please add 1 to all these no's
 */
/*pagination displaying no limit*/
const PAGING_MAX_DISPLAY_LIMIT=5;
/*pagination */
const PAGING_LAST_CONST_DISPLAY=4;
/*pagination initial iteration limit*/
const PAGING_ITERATION_LIMIT=6;
/*pagination active page need to display in second last*/
const PAGING_NEXT_ACTIVE_POSITION_LIMIT=2;

const TRUE="true";
const FALSE="false";


/**
 * constants for reservation list page
 */
const CONFIRMED="CONFIRMED";
const UNCONFIRMED="UNCONFIRMED";
const CANCELLED="CANCELLED";
const CHECK_IN="CHECK-IN";
const PART_CHECK_IN="PART CHECK-IN";


const CORPORATE_CLASS = 1;
const TRAVELAGENT_CLASS = 2;
 
const COPORATE_TYPE = "CORPORATE";                            
const TRAVELAGENT_TYPE = "TRAVELAGENT";
 