import { switchTab } from "./utilities.js";
import {
    displayHouseInput,
    createUpdateUserCard,
    displayUpdateUserCard,
    showHouseIDInputUpdate
} from "./user.js";
import { createFacilityCard, displayBelongToInput, createUpdateFacilityCard, displayUpdateFacilityCard, updateFacilityData,showBelongToIDInputUpdate } from "./facility.js";
import { getHiddenValue,
    createUpdateRuleCard,
    displayUpdateRuleCard} from "./rule.js";   
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

            document.getElementById("additionalInputContainer").replaceChildren();
        }
    }
    ));
});

// Handle nested dropdown toggle
document.querySelectorAll('.dropdown-toggle-custom').forEach(toggle => {
    toggle.addEventListener('click', function (e) {

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

document.addEventListener("click", (e) => {
    if (e.target.classList.contains("update_user_button")) {
        e.preventDefault();

        const updateUserUI = createUpdateUserCard();
        displayUpdateUserCard(e, updateUserUI, overlay);
        showHouseIDInputUpdate(updateUserUI);

        if (updateUserUI.cancelBtn) {
            updateUserUI.cancelBtn.addEventListener("click", () => {
                overlay.remove();
            });
        }
    }
});

document.addEventListener("click", (e) => {
    if (e.target.classList.contains("cancel_update_user_button")) {
        
        overlay.remove();
    }
});

/*document.addEventListener("click", (e) => {
    if (e.target.classList.contains("delete_user_button")) {
        const userCard = e.target.closest(".user_card");
        if(userCard){
                    userCard.parentElement.remove();

        }
    }
});*/

//Facility
selectType.addEventListener("change", () => {
    displayBelongToInput(selectType);
});
//create facility card
document.getElementById("submit_facility_button").addEventListener("click", (e) => {
    
        let submitFacilityButton = document.getElementById("submit_facility_button");
    let facilityTypeValue = document.getElementById("facility_type_select").value;
    switch(facilityTypeValue){
        case "1":
            submitFacilityButton.value = "CreateHome";
                    break;
        case "2":
            submitFacilityButton.value = "CreateRoom";
                    break;
        case "3":
            submitFacilityButton.value = "CreateDevice";
        default:
            console.log("No facility found");
            break;
    }
    //createFacilityCard(e, selectType);

});



//Update facility card
document.addEventListener("click", (e) => {
    if (e.target.classList.contains("update_facility_button")) {
        const updateFacilityUI = createUpdateFacilityCard();

        displayUpdateFacilityCard(e, updateFacilityUI, overlay);

        if (updateFacilityUI.cancelBtn) {
            updateFacilityUI.cancelBtn.addEventListener("click", () => {
                overlay.remove();
            });
        }
    }
    
});


document.addEventListener("click", (e) => {
    if (e.target.classList.contains("cancel_update_facility_button")) {
        
        overlay.remove();
    }
});

/*document.addEventListener("click", (e) => {
    if (e.target.classList.contains("delete_facility_button")) {
        if(facilityCard){
            facilityCard.parentElement.remove();
        }
        
    }
});*/
document.addEventListener("click", (e) => {
    if (e.target.classList.contains("update_rule_button")) {
        e.preventDefault();

        const updateRuleUI = createUpdateRuleCard();
        displayUpdateRuleCard(e, updateRuleUI, overlay);

        if (updateRuleUI.cancelBtn) {
            updateRuleUI.cancelBtn.addEventListener("click", () => {
                overlay.remove();
            });
        }
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
        if(ruleCard){
                    ruleCard.parentElement.remove();
        }
    }
});
