import { switchTab } from "./utilities.js";

const navLinks = document.querySelectorAll('.nav-link');
const itemList = document.querySelectorAll('.item_list');

//Handle switch tab
document.addEventListener("DOMContentLoaded", () => {
    navLinks.forEach(link => link.addEventListener("click", (e) => {
        if (e.target.classList.contains("tab_display")) {
            switchTab(navLinks, itemList, e.target);
        }
    }
    ));
});

