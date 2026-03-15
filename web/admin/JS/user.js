function displayHouseInput(selectRole) {
    const role = selectRole.value;
    let houseInput = document.getElementById("houseInput");

    // Value "2" tương ứng với Home Owner trong thẻ select của admin.jsp
    if (role === "2") {
        if (!houseInput) {
            houseInput = document.createElement("input");
            houseInput.id = "houseInput";
            houseInput.name = "txtHouseId";
            houseInput.className = "form-control mx-auto mt-3 input_data";
            houseInput.placeholder = "Enter House Code (e.g., HOME-01)"; // Sửa chữ để user biết nhập Code

            document.getElementById("create_user_form").insertBefore(
                houseInput,
                document.getElementById("create_user_button_container")
            );
        }
    } else {
        if (houseInput) {
            houseInput.remove();
        }
    }
}

function createUpdateUserCard() {
    const container = document.createElement("div");
    container.className = "container-fluid section_container";

    const card = document.createElement("div");
    card.className = "card card_container shadow border-primary";

    const form = document.createElement("form");
    form.className = "d-flex submit_form";
    form.action = "../MainController"; // Đã thêm ../ để tránh lỗi 404
    form.method = "post";

    const formTitle = document.createElement("h5");
    formTitle.className = "text-center mt-3 text-primary";
    formTitle.textContent = "Update User";

    const hiddenAction = document.createElement("input");
    hiddenAction.type = "hidden";
    hiddenAction.name = "action";
    hiddenAction.value = "UpdateUser";

    const hiddenUserId = document.createElement("input");
    hiddenUserId.type = "hidden";
    hiddenUserId.name = "txtUserId";

    const inputFullName = document.createElement("input");
    inputFullName.className = "form-control mx-auto mt-3 input_data";
    inputFullName.name = "txtFullName";
    inputFullName.placeholder = "Enter full name";

    const inputEmail = document.createElement("input");
    inputEmail.className = "form-control mx-auto mt-3 input_data";
    inputEmail.name = "txtEmail";
    inputEmail.placeholder = "Enter email";

    const selectRole = document.createElement("select");
    selectRole.className = "form-select mx-auto mt-3 input_data";
    selectRole.name = "txtRoleName";
    selectRole.innerHTML = `
        <option value="">Choose role</option>
        <option value="Admin">Admin</option>
        <option value="Home Owner">Home Owner</option>
        <option value="Technician">Technician</option>
        <option value="Viewer">Viewer</option>
    `;

    const selectStatus = document.createElement("select");
    selectStatus.className = "form-select mx-auto mt-3 input_data";
    selectStatus.name = "txtStatus";
    selectStatus.innerHTML = `
        <option value="">Choose status</option>
        <option value="1">Active</option>
        <option value="0">Inactive</option>
    `;

    const buttonContainer = document.createElement("div");
    buttonContainer.className = "button_container mt-3 d-flex justify-content-center gap-5";

    const updateBtn = document.createElement("button");
    updateBtn.type = "submit";
    updateBtn.className = "btn btn-outline-primary submit_update_user_button";
    updateBtn.textContent = "Update";

    const cancelBtn = document.createElement("button");
    cancelBtn.type = "button";
    cancelBtn.className = "btn btn-outline-danger cancel_update_user_button";
    cancelBtn.textContent = "Cancel";

    buttonContainer.appendChild(updateBtn);
    buttonContainer.appendChild(cancelBtn);

    form.append(
        hiddenAction,
        hiddenUserId,
        formTitle,
        inputFullName,
        inputEmail,
        selectRole,
        selectStatus,
        buttonContainer
    );

    card.appendChild(form);
    container.appendChild(card);

    return {
        container,
        form,
        hiddenUserId,
        inputFullName,
        inputEmail,
        selectRole,
        selectStatus,
        buttonContainer,
        cancelBtn
    };
}

function getHiddenValue(formSource, name) {
    const input = formSource.querySelector(`input[name="${name}"]`);
    return input ? input.value : "";
}

function displayUpdateUserCard(e, updateUI, overlay) {
    e.preventDefault();

    const formSource = e.target.closest("form");
    if (!formSource) return null;

    updateUI.hiddenUserId.value = getHiddenValue(formSource, "txtUserId");
    updateUI.inputFullName.value = getHiddenValue(formSource, "txtFullName");
    updateUI.inputEmail.value = getHiddenValue(formSource, "txtEmail");

    const roleName = getHiddenValue(formSource, "txtRoleName");
    [...updateUI.selectRole.options].forEach(opt => {
        opt.selected = opt.value === roleName;
    });

    const statusValue = getHiddenValue(formSource, "txtStatus");
    [...updateUI.selectStatus.options].forEach(opt => {
        opt.selected = opt.value === statusValue;
    });

    overlay.innerHTML = "";
    overlay.appendChild(updateUI.container);
    document.body.appendChild(overlay);

    return formSource;
}

function showHouseIDInputUpdate(updateUI) {
    const houseInput = document.createElement("input");
    houseInput.className = "form-control mx-auto mt-3 input_data";
    houseInput.name = "txtHouseId";
    houseInput.placeholder = "Enter House Code (e.g., HOME-01)";

    // 1. HIỂN THỊ NGAY LẬP TỨC NẾU ĐÃ LÀ HOME OWNER TỪ TRƯỚC
    if (updateUI.selectRole.value === "Home Owner") {
        updateUI.form.insertBefore(houseInput, updateUI.buttonContainer);
    }

    // 2. LẮNG NGHE NẾU ADMIN ĐỔI ROLE
    updateUI.selectRole.addEventListener("change", () => {
        const role = updateUI.selectRole.value;
        const existed = houseInput.isConnected;

        if (role === "Home Owner" && !existed) {
            updateUI.form.insertBefore(houseInput, updateUI.buttonContainer);
        } else if (role !== "Home Owner" && existed) {
            houseInput.remove();
        }
    });
}

export {
    displayHouseInput,
    createUpdateUserCard,
    displayUpdateUserCard,
    showHouseIDInputUpdate
};