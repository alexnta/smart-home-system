
//generate ID
let IDList = [];
function generateID(arr, min = 1, max = 100) {
    let randomNumber = Math.floor(Math.random() * (max - min + 1)) + min;
    while (arr.includes(randomNumber)) {
        randomNumber++;
    }
    arr.push(randomNumber);
    return randomNumber;
}

function createFacilityCard(e, selectFacilityType) {
    e.preventDefault();

    let facilityCardContainer = document.createElement("div");
    facilityCardContainer.className = "col-6 col-lg-3 card_container";
    let facilityCard = document.createElement("div");
    facilityCard.className = "card facility_card";
    let facilityCardBody = document.createElement("div");
    facilityCardBody.className = "card-body";
    let facilityCardTitle = document.createElement("h5");
    facilityCardTitle.className = "card-title";
    facilityCardTitle.textContent = "Facility name: " + document.getElementsByClassName("input_facility_name")[0].value;
    let facilityCardType = document.createElement("p");
    facilityCardType.className = "card-text";
    facilityCardType.textContent = "Facility type: " + selectFacilityType.options[selectFacilityType.selectedIndex].text;
    let facilityIDText = document.createElement("p");
    facilityIDText.className = "card-text";
    
        let facilityStatus = document.createElement("p");
    facilityStatus.className = "card-text";
    facilityStatus.textContent =  "Status: " + document.getElementById("status_type_select").options[document.getElementById("status_type_select").selectedIndex].text;

//EDIT THIS AREA
    let belongToInputID = document.getElementById("belongToInput");

    let facilityCardID = document.createElement("p");
    facilityCardID.className = "card-text";
    facilityCardID.textContent = "Facility ID: " + document.getElementsByClassName("input_facility_ID")[0].value;

    let updateFacilityButton = document.createElement("button");
    updateFacilityButton.className = "btn btn-primary update_facility_button";
    updateFacilityButton.textContent = "Update";

    let deleteFacilityButton = document.createElement("button");
    deleteFacilityButton.className = "btn btn-danger delete_facility_button mx-2";
    deleteFacilityButton.textContent = "Delete";

    let facilityContainerButtons = document.createElement("div");
    facilityContainerButtons.className = "facility_card_button_container d-flex mt-1 mb-2 justify-content-center gap-3";
    facilityContainerButtons.appendChild(updateFacilityButton);
    facilityContainerButtons.appendChild(deleteFacilityButton);
    facilityCardBody.appendChild(facilityCardTitle);
    facilityCardBody.appendChild(facilityCardType);
    facilityCardBody.appendChild(facilityStatus);
    facilityCardBody.appendChild(facilityIDText);
    facilityCardBody.appendChild(facilityCardID);
    facilityCard.appendChild(facilityCardBody);
    facilityCardContainer.appendChild(facilityCard);
    facilityCard.appendChild(facilityContainerButtons);
    //test
    switch(selectFacilityType.value){
        case "1":
            let addressText = document.createElement("p");
            addressText.className = "card-text";
            addressText.textContent = "Address: " + document.getElementById("belongToInput").value;
            
            facilityIDText.textContent = "Belong to owner : " + document.getElementById("houseOwnerIDInput").value;
            facilityCardBody.appendChild(addressText);


            document.getElementById("house_management_section").appendChild(facilityCardContainer);
            break;
        case "2":
            // let belongToHouseIDTexrt = document.createElement("p");
            // belongToHouseIDTexrt.className = "card-text";
            // belongToHouseIDTexrt.textContent = "Belong to house: " + belongToInputID.value;
            // facilityCardBody.insertBefore(belongToHouseIDTexrt, facilityCardID);
                        let roomTypeInput = document.createElement("p");
            roomTypeInput.className = "card-text";
            roomTypeInput.textContent = document.getElementById("roomTypeInput").value;
facilityCardBody.appendChild(roomTypeInput);

facilityIDText.textContent = "Belong to house: " + belongToInputID.value.value;

                        let floor = document.createElement("p");
            floor.className = "card-text";
            floor.textContent = "floor: " +  document.getElementById("floorInput").value;
facilityCardBody.appendChild(floor);
            document.getElementById("room_management_section").appendChild(facilityCardContainer);
            break;
        case "3":
            // let belongToRoomIDTexrt = document.createElement("p");
            // belongToRoomIDTexrt.className = "card-text";
            // belongToRoomIDTexrt.textContent = "Belong to room: " + belongToInputID.value;
            // facilityCardBody.insertBefore(belongToRoomIDTexrt, facilityCardID);
            let deviceTypeInput = document.createElement("p");
            deviceTypeInput.className = "card-text";
            deviceTypeInput.textContent = "Device type: " + document.getElementById("deviceTypeInput").value;
            facilityCardBody.appendChild(deviceTypeInput);
            
                        let vendorInput = document.createElement("p");
            vendorInput.className = "card-text";
            vendorInput.textContent = "Vendor: " + document.getElementById("vendorInput").value;
            facilityCardBody.appendChild(vendorInput);
            
                                    let conditionInputForDevice = document.createElement("p");
            conditionInputForDevice.className = "card-text";
            conditionInputForDevice.textContent = "Condition: " + document.getElementById("conditionInputForDevice").value;
            facilityCardBody.appendChild(conditionInputForDevice);
            facilityIDText.textContent = "Belong to room: " + belongToInputID.value;
            document.getElementById("device_management_section").appendChild(facilityCardContainer);
            break;
    }

    document.getElementById("create_facility_form").reset();
const belongInput = document.getElementById("belongToInput");
if (belongInput) {
    belongInput.remove();
}
    // createUserCardContainer.remove();
    // overlay.remove();
}
function displayBelongToInput(selectFacilityType){
    const type = selectFacilityType.value;
    let belongToInput = document.getElementById("belongToInput");
    let deviceTypeInput = document.getElementById("deviceTypeInput");
    let houseOwnerID = document.getElementById("houseOwnerIDInput");
    let roomTypeInput = document.getElementById("roomTypeInput");
    let vendorInput = document.getElementById("vendorInput");
    let floorInput = document.getElementById("floorInput");
    let conditionInputForDevice = document.getElementById("conditionInputForDevice");

    if (type === "2") {
        if(deviceTypeInput){
            deviceTypeInput.remove();
        }
                if(conditionInputForDevice){
            conditionInputForDevice.remove();
        }
        if(houseOwnerID){
            houseOwnerID.remove();
        }
        if(vendorInput){
            vendorInput.remove();
        }
        if (!roomTypeInput) {
    roomTypeInput = document.createElement("input");
    roomTypeInput.setAttribute("id", "roomTypeInput");
    roomTypeInput.className = "form-control mx-auto mt-3 input_data";
    roomTypeInput.placeholder = "Enter room type";
    roomTypeInput.name = "txtRoomType";
    document.getElementById("additionalInputContainer").appendChild(roomTypeInput);
}

if (!floorInput) {
    floorInput = document.createElement("input");
    floorInput.type = "number";
    floorInput.setAttribute("id", "floorInput");
    floorInput.className = "form-control mx-auto mt-3 input_data";
    floorInput.placeholder = "Enter floor number";
    floorInput.name = "txtFloor";
    document.getElementById("additionalInputContainer").appendChild(floorInput);
}

if (!belongToInput) {
    belongToInput = document.createElement("input");
    belongToInput.setAttribute("id", "belongToInput");
    belongToInput.className = "form-control mx-auto mt-3 input_data";
    belongToInput.placeholder = "Enter house ID";
    belongToInput.name = "txtHomeId";
    document.getElementById("create_facility_form").insertBefore(
        belongToInput,
        document.getElementById("create_facility_button_container")
    );
} else {
    belongToInput.placeholder = "Enter house ID";
    belongToInput.name = "txtHomeId";
}
    } else if (type === "3") {
    if (houseOwnerID) {
        houseOwnerID.remove();
    }

    if (floorInput) {
        floorInput.remove();
    }

    if (roomTypeInput) {
        roomTypeInput.remove();
    }

    if (!deviceTypeInput) {
        deviceTypeInput = document.createElement("select");
        deviceTypeInput.className = "form-select mx-auto mt-3 input_data";
        deviceTypeInput.id = "deviceTypeInput";
        deviceTypeInput.name = "txtDeviceType";
        deviceTypeInput.innerHTML = `
            <option selected>Choose device type</option>
            <option value="Door">Door</option>
            <option value="Light">Light</option>
        `;
        document.getElementById("additionalInputContainer").appendChild(deviceTypeInput);
    }

    if (!conditionInputForDevice) {
        conditionInputForDevice = document.createElement("select");
        conditionInputForDevice.className = "form-select mx-auto mt-3 input_data";
        conditionInputForDevice.id = "conditionInputForDevice";
        conditionInputForDevice.name = "txtStatus";
        conditionInputForDevice.innerHTML = `
            <option selected>Choose condition</option>
        `;
        document.getElementById("additionalInputContainer").appendChild(conditionInputForDevice);

        deviceTypeInput.addEventListener("change", () => {
            switch (deviceTypeInput.value) {
                case "Door":
                    conditionInputForDevice.innerHTML = `
                        <option selected>Choose condition</option>
                        <option value="Opened">Opened</option>
                        <option value="Closed">Closed</option>
                    `;
                    break;
                case "Light":
                    conditionInputForDevice.innerHTML = `
                        <option selected>Choose condition</option>
                        <option value="On">On</option>
                        <option value="Off">Off</option>
                    `;
                    break;
            }
        });
    }

    if (!vendorInput) {
        vendorInput = document.createElement("input");
        vendorInput.id = "vendorInput";
        vendorInput.className = "form-control mx-auto mt-3 input_data";
        vendorInput.placeholder = "Enter vendor";
        vendorInput.name = "txtVendor";

        document.getElementById("create_facility_form").insertBefore(
            vendorInput,
            document.getElementById("create_facility_button_container")
        );
    }

    if (!belongToInput) {
        belongToInput = document.createElement("input");
        belongToInput.id = "belongToInput";
        belongToInput.className = "form-control mx-auto mt-3 input_data";
        belongToInput.placeholder = "Enter room ID";
        belongToInput.name = "txtRoomId";

        document.getElementById("create_facility_form").insertBefore(
            belongToInput,
            document.getElementById("create_facility_button_container")
        );
    } else {
        belongToInput.placeholder = "Enter room ID";
        belongToInput.name = "txtRoomId";
    }
} else if(type === "1"){
        console.log( document.getElementById("additionalInputContainer"));
                if(roomTypeInput){
            roomTypeInput.remove();
        }
                        if(conditionInputForDevice){
            conditionInputForDevice.remove();
        }
                if(vendorInput){
            vendorInput.remove();
        }
        if(deviceTypeInput){
            deviceTypeInput.remove();
        }
        
                
                       if(floorInput){
            floorInput.remove();
        }
            if(!houseOwnerID){
            houseOwnerID = document.createElement("input");
            houseOwnerID.setAttribute("id", "houseOwnerIDInput");
            houseOwnerID.className = "form-control mx-auto mt-3 input_data";
            houseOwnerID.placeholder = "Enter house owner ID";
            houseOwnerID.name = "txtOwnerId";
            
                        document.getElementById("create_facility_form").insertBefore(
                houseOwnerID,
                document.getElementById("create_facility_button_container")
            );
          /*  document.getElementById("additionalInputContainer").appendChild(
                houseOwnerID
            );*/
        }
        
        if (!belongToInput) {

            belongToInput = document.createElement("input");
            belongToInput.setAttribute("id", "belongToInput");
            belongToInput.className = "form-control mx-auto mt-3 input_data";
            belongToInput.placeholder = "Enter address";
            belongToInput.name = "txtAddress"

            document.getElementById("create_facility_form").insertBefore(
                belongToInput,
                document.getElementById("create_facility_button_container")
            );
        } else if(belongToInput){
            belongToInput.placeholder = "Enter address";
        }
    } else {
                               if(conditionInputForDevice){
            conditionInputForDevice.remove();
        }
                
                       if(floorInput){
            floorInput.remove();
        }
        if(belongToInput){
             belongToInput.remove();
        }
        if(deviceTypeInput){
            deviceTypeInput.remove();
        }
                        if(roomTypeInput){
            roomTypeInput.remove();
        }
        
                       if(houseOwnerID){
            houseOwnerID.remove();
        }
        
    }
}

//create update facility card function
function createUpdateFacilityCard() {
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
    formTitle.textContent = "Update Facility";

    const hiddenAction = document.createElement("input");
    hiddenAction.type = "hidden";
    hiddenAction.name = "action";

    const dynamicContainer = document.createElement("div");
    dynamicContainer.className = "dynamic_update_fields";

    const buttonContainer = document.createElement("div");
    buttonContainer.className = "button_container mt-3 d-flex justify-content-center gap-5";

    const updateBtn = document.createElement("button");
    updateBtn.type = "submit";
    updateBtn.className = "btn btn-outline-success submit_update_facility_button";
    updateBtn.textContent = "Update";

    const cancelBtn = document.createElement("button");
    cancelBtn.type = "button";
    cancelBtn.className = "btn btn-outline-danger cancel_update_facility_button";
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

function createInput(name, value = "", placeholder = "", type = "text", hidden = false) {
    const input = document.createElement("input");
    input.type = hidden ? "hidden" : type;
    input.name = name;
   input.value = (value !== null && value !== undefined) ? value : "";
    if (!hidden) {
        input.className = "form-control mx-auto mt-3 input_data";
        input.placeholder = placeholder;
    }
    return input;
}

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

function getHiddenValue(card, name) {
    const input = card.querySelector(`input[name="${name}"]`);
    return input ? input.value : "";
}

function renderUpdateFields(updateUI, card) {
    const body = card.querySelector(".card-body");
    const typeText = body.querySelectorAll(".card-text")[0].textContent
        .replace("Facility type: ", "")
        .trim();

    updateUI.dynamicContainer.innerHTML = "";

    if (typeText === "House") {
        updateUI.hiddenAction.value = "UpdateHome";

        updateUI.dynamicContainer.appendChild(
            createInput("txtHomeId", getHiddenValue(card, "txtHomeId"), "", "text", true)
        );
        updateUI.dynamicContainer.appendChild(
            createInput("txtCode", getHiddenValue(card, "txtCode"), "Enter code")
        );
        updateUI.dynamicContainer.appendChild(
            createInput("txtName", getHiddenValue(card, "txtName"), "Enter home name")
        );
        updateUI.dynamicContainer.appendChild(
            createInput("txtAddress", getHiddenValue(card, "txtAddress"), "Enter address")
        );
        updateUI.dynamicContainer.appendChild(
            createInput("txtOwnerId", getHiddenValue(card, "txtOwnerId"), "Enter owner ID", "number")
        );
        updateUI.dynamicContainer.appendChild(
            createSelect("txtStatus", getHiddenValue(card, "txtStatus"), [
                { value: "Active", label: "Active" },
                { value: "Inactive", label: "Inactive" }
            ])
        );

    } else if (typeText === "Room") {
        updateUI.hiddenAction.value = "UpdateRoom";

        updateUI.dynamicContainer.appendChild(
            createInput("txtRoomId", getHiddenValue(card, "txtRoomId"), "", "text", true)
        );
        updateUI.dynamicContainer.appendChild(
            createInput("txtHomeId", getHiddenValue(card, "txtHomeId"), "Enter home ID", "number")
        );
        updateUI.dynamicContainer.appendChild(
            createInput("txtName", getHiddenValue(card, "txtName"), "Enter room name")
        );
        updateUI.dynamicContainer.appendChild(
            createInput("txtRoomType", getHiddenValue(card, "txtRoomType"), "Enter room type")
        );
        updateUI.dynamicContainer.appendChild(
            createInput("txtFloor", getHiddenValue(card, "txtFloor"), "Enter floor", "number")
        );
        updateUI.dynamicContainer.appendChild(
            createSelect("txtStatus", getHiddenValue(card, "txtStatus"), [
                { value: "Active", label: "Active" },
                { value: "Inactive", label: "Inactive" }
            ])
        );

    } else if (typeText === "Device") {
        updateUI.hiddenAction.value = "UpdateDevice";

        updateUI.dynamicContainer.appendChild(
            createInput("txtDeviceId", getHiddenValue(card, "txtDeviceId"), "", "text", true)
        );
        updateUI.dynamicContainer.appendChild(
            createInput("txtRoomId", getHiddenValue(card, "txtRoomId"), "Enter room ID", "number")
        );
        updateUI.dynamicContainer.appendChild(
            createInput("txtName", getHiddenValue(card, "txtName"), "Enter device name")
        );
        updateUI.dynamicContainer.appendChild(
            createInput("txtDeviceType", getHiddenValue(card, "txtDeviceType"), "Enter device type")
        );
        updateUI.dynamicContainer.appendChild(
            createInput("txtSerialNo", getHiddenValue(card, "txtSerialNo"), "Enter serial no")
        );
        updateUI.dynamicContainer.appendChild(
            createInput("txtVendor", getHiddenValue(card, "txtVendor"), "Enter vendor")
        );
        updateUI.dynamicContainer.appendChild(
            createInput("txtIsActive", getHiddenValue(card, "txtIsActive") || "true", "", "text", true)
        );
        updateUI.dynamicContainer.appendChild(
            createSelect("txtStatus", getHiddenValue(card, "txtStatus"), [
                { value: "On", label: "On" },
                { value: "Off", label: "Off" },
                { value: "Opened", label: "Opened" },
                { value: "Closed", label: "Closed" },
                { value: "Locked", label: "Locked" },
                { value: "Unlocked", label: "Unlocked" }
            ])
        );
    }
}

function displayUpdateFacilityCard(e, updateUI, overlay) {
    e.preventDefault();

    const card = e.target.closest(".facility_card");
    renderUpdateFields(updateUI, card);

    overlay.innerHTML = "";
    overlay.appendChild(updateUI.container);
    document.body.appendChild(overlay);

    return card;
}



function updateFacilityData(e, card, overlay, updateUI) {
    e.preventDefault();
    if (!card) return;

    const body = card.querySelector(".card-body");

    // 1. Update tên
body.querySelector(".card-title").textContent =
    "Facility name: " + updateUI.inputFacilityName.value;

    // 2. Update type text
    const typeTextEl = body.querySelectorAll(".card-text")[0];
    const selectedOption = updateUI.selectType.options[updateUI.selectType.selectedIndex];
typeTextEl.textContent = "Facility type: " + selectedOption.text;

    const typeValue = selectedOption.value;

    // 3. Tìm "Belong To" hiện có (nếu có)
    let belongToEl = [...body.querySelectorAll(".card-text")]
        .find(p => p.textContent.startsWith("Belong"));

    // 4. Lấy giá trị input belong (nếu có)
const belongInput = updateUI.form.querySelector("#belongToInput");
const belongValue = belongInput ? belongInput.value : "";
   
    

    // ===== CASE LOGIC =====

    if (typeValue === "1") {
        // HOUSE → không có belong
        if(belongToEl){
            belongToEl.remove();
        }
        document.getElementById("house_management_section")
            .appendChild(card.parentElement);

    } else {
        // ROOM hoặc DEVICE → cần belong
        const label = typeValue === "2" ? "Belong to house" : "Belong to room";

        if (!belongToEl) {
            // chưa có → tạo mới
            belongToEl = document.createElement("p");
            belongToEl.className = "card-text";
            body.insertBefore(belongToEl, body.querySelectorAll(".card-text")[1]);
        }

        // update text
        belongToEl.textContent = `${label} : ${belongValue}`;

        // move card
        const targetSection = typeValue === "2"
            ? "room_management_section"
            : "device_management_section";

        document.getElementById(targetSection)
            .appendChild(card.parentElement);
    }

    overlay.remove();
}


function showBelongToIDInputUpdate(updateUI) {
    const belongToInput = document.createElement("input");
    belongToInput.setAttribute("id", "belongToInput");
    belongToInput.className = "form-control mx-auto mt-3 input_data";
    belongToInput.placeholder = "Enter House ID";

    updateUI.selectType.addEventListener("change", () => {
        const role = updateUI.selectType.value;
        const existed = belongToInput.isConnected;

if ((role === "2" || role === "3") && !existed) {
    belongToInput.placeholder = role === "2" ? "Enter House ID" : "Enter Room ID";
    updateUI.form.insertBefore(belongToInput, updateUI.buttonContainer);
} else if (role === "1" && existed) {
    belongToInput.remove();
}
    });
}


export{
    createFacilityCard,
    displayBelongToInput,
    createUpdateFacilityCard,
    displayUpdateFacilityCard,
    updateFacilityData,
    showBelongToIDInputUpdate
}