// Create update device card
function createUpdateDeviceCard() {
    const container = document.createElement("div");
    container.className = "container-fluid section_container";

    const card = document.createElement("div");
    card.className = "card card_container";

    const form = document.createElement("form");
    form.className = "d-flex submit_form";
    form.action = "MainController";
    form.method = "post";

    const formTitle = document.createElement("h5");
    formTitle.className = "text-center mt-3";
    formTitle.textContent = "Update Device";

    const hiddenAction = document.createElement("input");
    hiddenAction.type = "hidden";
    hiddenAction.name = "action";
    hiddenAction.value = "UpdateDevice";

    const dynamicContainer = document.createElement("div");
    dynamicContainer.className = "dynamic_update_fields";

    const buttonContainer = document.createElement("div");
    buttonContainer.className = "button_container mt-3 d-flex justify-content-center gap-3";

    const updateBtn = document.createElement("button");
    updateBtn.type = "submit";
    updateBtn.className = "btn btn-outline-success submit_update_device_button";
    updateBtn.textContent = "Update";

    const cancelBtn = document.createElement("button");
    cancelBtn.type = "button";
    cancelBtn.className = "btn btn-outline-danger cancel_update_device_button";
    cancelBtn.textContent = "Cancel";

    buttonContainer.appendChild(updateBtn);
    buttonContainer.appendChild(cancelBtn);

    form.append(formTitle, hiddenAction, dynamicContainer, buttonContainer);
    card.appendChild(form);
    container.appendChild(card);

    return {
        container,
        form,
        dynamicContainer,
        buttonContainer,
        hiddenAction,
        updateBtn,
        cancelBtn
    };
}

// Create input
function createInput(name, value = "", placeholder = "", type = "text", hidden = false) {
    const input = document.createElement("input");
    input.type = hidden ? "hidden" : type;
    input.name = name;
    input.value = value !== null && value !== undefined ? value : "";

    if (!hidden) {
        input.className = "form-control mx-auto mt-3 input_data";
        input.placeholder = placeholder;
    }

    return input;
}

// Create select
function createSelect(name, selectedValue, options) {
    const select = document.createElement("select");
    select.name = name;
    select.className = "form-select mx-auto mt-3 input_data";

    options.forEach(opt => {
        const option = document.createElement("option");
        option.value = opt.value;
        option.textContent = opt.label;

        if (String(opt.value) === String(selectedValue)) {
            option.selected = true;
        }

        select.appendChild(option);
    });

    return select;
}

// Get hidden value from card
function getHiddenValue(card, name) {
    const input = card.querySelector(`input[name="${name}"]`);
    return input ? input.value : "";
}

// Render only Device fields: deviceId, roomId, status
function renderUpdateDeviceFields(updateUI, card) {
    updateUI.dynamicContainer.innerHTML = "";

    const deviceId = getHiddenValue(card, "txtDeviceId");
    const roomId = getHiddenValue(card, "txtRoomId");
    const status = getHiddenValue(card, "txtStatus");

    // Hidden device id
    updateUI.dynamicContainer.appendChild(
        createInput("txtDeviceId", deviceId, "", "text", true)
    );

    // Room ID
    updateUI.dynamicContainer.appendChild(
        createInput("txtRoomId", roomId, "Enter room ID", "number")
    );

    // Status
    updateUI.dynamicContainer.appendChild(
        createSelect("txtStatus", status, [
            { value: "On", label: "On" },
            { value: "Off", label: "Off" },
            { value: "Opened", label: "Opened" },
            { value: "Closed", label: "Closed" },
            { value: "Locked", label: "Locked" },
            { value: "Unlocked", label: "Unlocked" }
        ])
    );
}
export{
    createUpdateDeviceCard,
renderUpdateDeviceFields
    
}