import { switchTab } from "./utilities.js";
import {
    createUpdateDeviceCard,
    renderUpdateDeviceFields
} from "./updateStatusAndRoomID.js";

const navLinks = document.querySelectorAll(".nav-link");
const itemList = document.querySelectorAll(".item_list");

// Handle switch tab
document.addEventListener("DOMContentLoaded", () => {
    navLinks.forEach(link =>
        link.addEventListener("click", (e) => {
            if (e.target.classList.contains("tab_display")) {
                switchTab(navLinks, itemList, e.target);
            }
        })
    );
});

// Update device card
document.addEventListener("click", (e) => {
    if (e.target.classList.contains("update_facility_button")) {
        const card = e.target.closest(".facility_card");
        if (!card) return;

        const updateDeviceUI = createUpdateDeviceCard();

        renderUpdateDeviceFields(updateDeviceUI, card);

        const overlay = document.createElement("div");
        overlay.className = "overlay";
        overlay.appendChild(updateDeviceUI.container);
        document.body.appendChild(overlay);

        updateDeviceUI.cancelBtn.addEventListener("click", () => {
            overlay.remove();
        });
    }
});