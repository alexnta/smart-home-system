import { switchTab } from "./utilities.js";
import {
    displayHouseInput,
    createUpdateUserCard,
    displayUpdateUserCard,
    showHouseIDInputUpdate
} from "./user.js";
import { createFacilityCard, displayBelongToInput, createUpdateFacilityCard, displayUpdateFacilityCard, updateFacilityData, showBelongToIDInputUpdate } from "./facility.js";
import { getHiddenValue, createUpdateRuleCard, displayUpdateRuleCard, initJsonValidatorForCreate } from './rule.js';

const navLinks = document.querySelectorAll('.nav-link');
const itemList = document.querySelectorAll('.item_list');
const selectRole = document.getElementById("role_select");
const selectType = document.getElementById("facility_type_select");

const overlay = document.createElement("div");
overlay.className = "overlay";

document.addEventListener("DOMContentLoaded", function() {
    initJsonValidatorForCreate(); // Kích hoạt bắt lỗi JSON
});

// Handle switch tab
document.addEventListener("DOMContentLoaded", () => {
    navLinks.forEach(link => link.addEventListener("click", (e) => {
        if (e.target.classList.contains("tab_display")) {
            switchTab(navLinks, itemList, e.target);
            document.getElementById("create_facility_form").reset();
            document.getElementById("create_user_form").reset();
            let input = document.getElementById("houseInput");
            if (input) {
                input.remove();
            }
            document.getElementById("create_rule_form").reset();
            document.getElementById("additionalInputContainer").replaceChildren();
        }
    }));
});

// Handle nested dropdown
document.querySelectorAll('.dropdown-toggle-custom').forEach(toggle => {
    toggle.addEventListener('click', function (e) {
        e.stopPropagation();
        this.classList.toggle('show');
    });
});

document.addEventListener('click', function (e) {
    document.querySelectorAll('.dropdown-toggle-custom.show').forEach(toggle => {
        if (!toggle.closest('.dropdown-item-wrapper').contains(e.target)) {
            toggle.classList.remove('show');
        }
    });
});

// ========================================================
// GLOBAL CLICK DELEGATION (XỬ LÝ TẤT CẢ CÁC NÚT BẤM)
// ========================================================
document.addEventListener("click", (e) => {

    // --- 1. XỬ LÝ NÚT CANCEL TẤT CẢ OVERLAY ---
    if (e.target.classList.contains("cancel_update_user_button") || 
        e.target.classList.contains("cancel_update_facility_button") || 
        e.target.classList.contains("cancel_update_rule_button")) {
        overlay.remove();
    }

    // --- 2. XỬ LÝ UPDATE USER ---
    if (e.target.classList.contains("update_user_button")) {
        e.preventDefault();
        const updateUserUI = createUpdateUserCard();
        displayUpdateUserCard(e, updateUserUI, overlay);
        showHouseIDInputUpdate(updateUserUI);
    }

    // --- 3. XỬ LÝ UPDATE FACILITY (Home/Room/Device) ---
    if (e.target.classList.contains("update_facility_button")) {
        e.preventDefault(); // Rất quan trọng để chặn form submit
        const card = e.target.closest(".card");
        const updateFacilityUI = createUpdateFacilityCard();
        displayUpdateFacilityCard(e, updateFacilityUI, overlay);
    }

    // --- 4. XỬ LÝ UPDATE RULE ---
    if (e.target.classList.contains("update_rule_button")) {
        e.preventDefault();
        const updateRuleUI = createUpdateRuleCard();
        displayUpdateRuleCard(e, updateRuleUI, overlay);
    }
});

// ========================================================
// XỬ LÝ DROPDOWNS (SELECT CHANGE)
// ========================================================
if(selectRole) {
    selectRole.addEventListener("change", () => {
        displayHouseInput(selectRole);
    });
}

if(selectType) {
    selectType.addEventListener("change", () => {
        displayBelongToInput(selectType);
    });
}

// ========================================================
// XỬ LÝ NÚT SUBMIT CREATE FACILITY 
// (Thay đổi Value linh hoạt theo loại Facility)
// ========================================================
const submitFacilityBtn = document.getElementById("submit_facility_button");
if(submitFacilityBtn) {
    submitFacilityBtn.addEventListener("click", (e) => {
        let facilityTypeValue = document.getElementById("facility_type_select").value;
        switch(facilityTypeValue){
            case "1": submitFacilityBtn.value = "CreateHome"; break;
            case "2": submitFacilityBtn.value = "CreateRoom"; break;
            case "3": submitFacilityBtn.value = "CreateDevice"; break;
            default: break;
        }
    });
}