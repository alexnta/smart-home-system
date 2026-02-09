import { switchTab } from "./utilities.js";
import {createUserCard,
    displayHouseInput,
    createUpdateUserCard,
    displayUpdateUserCard,
    updateData,
showHouseIDInputUpdate } from "./user.js";
const navLinks = document.querySelectorAll('.nav-link');
const itemList = document.querySelectorAll('.item_list');
const selectRole = document.getElementById("role_select");


const overlay = document.createElement("div");
overlay.className = "overlay";

//Handle switch tab
document.addEventListener("DOMContentLoaded", () => {
    navLinks.forEach(link => link.addEventListener("click", (e) => {
        if (e.target.classList.contains("tab_display")) {
            switchTab(navLinks, itemList, e.target);
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
        const updateUI = createUpdateUserCard();

        const editingCard = displayUpdateUserCard(
            e,
            updateUI,
            overlay
        );

        showHouseIDInputUpdate(updateUI);

        updateUI.form.addEventListener("submit", (ev) => {
            updateData(ev, editingCard, overlay, updateUI);
        });
    }
});

document.addEventListener("click", (e) => {
    if (e.target.classList.contains("cancel_update_button")) {
        
        overlay.remove();
    }
});


