import { switchTab } from "./utilities.js";
import {createUserCard,
    displayHouseInput,
    createUpdateUserCard,
    displayUpdateUserCard,
    updateUserData,
showHouseIDInputUpdate } from "./user.js";
import { createFacilityCard, displayBelongToInput, createUpdateFacilityCard, displayUpdateFacilityCard, updateFacilityData,showBelongToIDInputUpdate } from "./facility.js";
import { changeRuleType, createRuleCard, createUpdateRuleCard, displayUpdateRuleCard, updateRuleData, changeRuleTypeUpdate} from "./rule.js";   
const navLinks = document.querySelectorAll('.nav-link');
const itemList = document.querySelectorAll('.item_list');
const selectRole = document.getElementById("role_select");
const selectType = document.getElementById("facility_type_select");


const overlay = document.createElement("div");
overlay.className = "overlay";

//Handle switch tab
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
            document.getElementById("rule_options_container").innerHTML = "";
        }
    }
    ));
});

// Handle nested dropdown toggle
document.querySelectorAll('.dropdown-toggle-custom').forEach(toggle => {
    toggle.addEventListener('click', function (e) {
        e.preventDefault();
        e.stopPropagation();
        this.classList.toggle('show');
    });
});

// Close nested dropdown when clicking elsewhere
document.addEventListener('click', function (e) {
    document.querySelectorAll('.dropdown-toggle-custom.show').forEach(toggle => {
        if (!toggle.closest('.dropdown-item-wrapper').contains(e.target)) {
            toggle.classList.remove('show');
        }
    });
});
//USER
//Display house inpput
selectRole.addEventListener("change", () => {
    displayHouseInput(selectRole);
});
//create user card
document.getElementById("submit_user_button").addEventListener("click", (e) => {
    e.preventDefault();
    createUserCard(e, selectRole);
});



//Update user card
document.addEventListener("click", (e) => {
    if (e.target.classList.contains("update_user_button")) {
        const updateUserUI = createUpdateUserCard();

        const editingUserCard = displayUpdateUserCard(
            e,
            updateUserUI,
            overlay
        );

        showHouseIDInputUpdate(updateUserUI);

        updateUserUI.form.addEventListener("submit", (ev) => {
            updateUserData(ev, editingUserCard, overlay, updateUserUI);
        });
    }
});

document.addEventListener("click", (e) => {
    if (e.target.classList.contains("cancel_update_user_button")) {
        
        overlay.remove();
    }
});

document.addEventListener("click", (e) => {
    if (e.target.classList.contains("delete_user_button")) {
        const userCard = e.target.closest(".user_card");
        userCard?.parentElement.remove();
    }
});

//Facility
selectType.addEventListener("change", () => {
    displayBelongToInput(selectType);
});
//create facility card
document.getElementById("submit_facility_button").addEventListener("click", (e) => {
    e.preventDefault();
    createFacilityCard(e, selectType);
});



//Update facility card
document.addEventListener("click", (e) => {
    if (e.target.classList.contains("update_facility_button")) {
        const updateFacilityUI = createUpdateFacilityCard();

        const editingCard = displayUpdateFacilityCard(
            e,
            updateFacilityUI,
            overlay
        );

        showBelongToIDInputUpdate(updateFacilityUI);

        updateFacilityUI.form.addEventListener("submit", (ev) => {
            updateFacilityData(ev, editingCard, overlay, updateFacilityUI);
        });
    }
});


document.addEventListener("click", (e) => {
    if (e.target.classList.contains("cancel_update_facility_button")) {
        
        overlay.remove();
    }
});

document.addEventListener("click", (e) => {
    if (e.target.classList.contains("delete_facility_button")) {
        const facilityCard = e.target.closest(".facility_card");
        facilityCard?.parentElement.remove();
    }
});

//RULE
document.getElementById("rule_options_select").addEventListener("change",()=>{
    changeRuleType();
});

//create rule card
document.getElementById("submit_rule_button").addEventListener("click",(e)=>{
    e.preventDefault();
    createRuleCard();
});

document.addEventListener("click", (e) => {
    if (e.target.classList.contains("update_rule_button")) {

        const updateRuleUI = createUpdateRuleCard();

        const editingCard = displayUpdateRuleCard(
            e,
            updateRuleUI,
            overlay
        );

        updateRuleUI.form.addEventListener("submit", (ev) => {
            updateRuleData(ev, editingCard, overlay, updateRuleUI);
        });
    }
});

document.addEventListener("click", (e) => {
    if (e.target.classList.contains("cancel_update_rule_button")) {
        overlay.remove();
    }
});

document.addEventListener("click", (e) => {
    if (e.target.classList.contains("delete_rule_button")) {
        const ruleCard = e.target.closest(".rule_card");
        ruleCard?.parentElement.remove();
    }
});

document.addEventListener("change", (e) => {
    if (e.target.id === "update_rule_type_select") {
            changeRuleTypeUpdate();
    }
});