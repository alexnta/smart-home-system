function switchTab(navLinks, itemList, element) {
            const targetId = element.getAttribute('data-target');
            if (!targetId) return;
            navLinks.forEach(links =>
                links.classList.remove('active'));
            element.classList.add('active');
            itemList.forEach(section => section.style.display = 'none');

            const targetSection = document.getElementById(targetId);
            console.log(targetSection);
            if (targetSection) {
                targetSection.style.display = 'flex';
            }
        }
        export {
switchTab
        }