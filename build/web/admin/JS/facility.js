
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

function displayBelongToInput(selectFacilityType) {
    const type = selectFacilityType.value;
    const container = document.getElementById("additionalInputContainer");
    const submitBtn = document.getElementById("submit_facility_button");

    container.innerHTML = ""; // Dọn dẹp form

    if (type === "1") {
        // --- 1. HOUSE: Cần Code, Address, Owner, Status ---
        submitBtn.value = "CreateHome";
        container.innerHTML = `
            <input class="form-control mx-auto mt-3 mb-0 input_data" type="text" name="txtCode" placeholder="Enter House Code (e.g. H01)" required>
            <input class="form-control mx-auto mt-3 mb-0 input_data" type="text" name="txtAddress" placeholder="Enter House Address">
            <input class="form-control mx-auto mt-3 mb-0 input_data" type="number" name="txtOwnerId" placeholder="Enter Owner User ID (Optional)">
            <select class="form-select mx-auto mt-3 mb-0 input_data" name="txtStatus">
                <option value="Active" selected>Status: Active</option>
                <option value="Inactive">Status: Inactive</option>
                <option value="Maintenance">Status: Maintenance</option>
            </select>
        `;

    } else if (type === "2") {
        // --- 2. ROOM: Cần Home_ID, Floor, RoomType, Status (KHÔNG CÓ CODE) ---
        submitBtn.value = "CreateRoom";
        container.innerHTML = `
            <input class="form-control mx-auto mt-3 mb-0 input_data" type="number" name="txtHomeId" placeholder="Enter belonging House ID" required>
            <input class="form-control mx-auto mt-3 mb-0 input_data" type="number" name="txtFloor" placeholder="Enter floor number (Default: 1)" value="1">
            <select class="form-select mx-auto mt-3 mb-0 input_data" name="txtRoomType">
                <option value="" selected disabled>Choose Room Type</option>
                <option value="LivingRoom">Living Room</option>
                <option value="BedRoom">Bed Room</option>
                <option value="Kitchen">Kitchen</option>
                <option value="BathRoom">Bath Room</option>
            </select>
            <select class="form-select mx-auto mt-3 mb-0 input_data" name="txtStatus">
                <option value="Active" selected>Status: Active</option>
                <option value="Inactive">Status: Inactive</option>
            </select>
        `;

    } else if (type === "3") {
        // --- 3. DEVICE: Cần Room_ID, Serial, DeviceType, Vendor (Status mặc định Backend lo) ---
        submitBtn.value = "CreateDevice";
        container.innerHTML = `
            <input class="form-control mx-auto mt-3 mb-0 input_data" type="text" name="txtCode" placeholder="Enter Serial Number" required>
            
            <input class="form-control mx-auto mt-3 mb-0 input_data" type="number" id="ajax_home_id" name="txtHomeId" placeholder="Step 1: Enter House ID" required>
            
            <select class="form-select mx-auto mt-3 mb-0 input_data" id="ajax_room_select" name="txtRoomId">
                <option value="0">Step 2: Enter House ID first...</option>
            </select>

            <select class="form-select mx-auto mt-3 mb-0 input_data" name="txtDeviceType" required>
                <option value="" selected disabled>Choose Device Type</option>
                <option value="DoorSensor">Door Sensor</option>
                <option value="SmartLock">Smart Lock</option>
                <option value="LightSwitch">Light Switch</option>
                <option value="SmartLight">Smart Light</option>
            </select>
            <input class="form-control mx-auto mt-3 mb-0 input_data" type="text" name="txtVendor" placeholder="Enter Vendor">
        `;

        // BẮT ĐẦU XỬ LÝ AJAX
        const homeInput = document.getElementById("ajax_home_id");
        const roomSelect = document.getElementById("ajax_room_select");

        // Khi gõ xong ID nhà và bấm ra ngoài (hoặc gõ xong)
        homeInput.addEventListener("change", function() {
            const hId = this.value;
            if (!hId) return;

            roomSelect.innerHTML = '<option>Loading rooms...</option>';

            // Gọi Servlet lấy dữ liệu
            fetch(`../GetRoomsController?homeId=${hId}`)
                .then(res => res.json())
                .then(data => {
                    roomSelect.innerHTML = '<option value="0">-- Select Room (Optional) --</option>';
                    if (data.length === 0) {
                        roomSelect.innerHTML = '<option value="0">No rooms found in this house!</option>';
                    } else {
                        data.forEach(room => {
                            let opt = document.createElement("option");
                            opt.value = room.id;
                            opt.textContent = `${room.name} (ID: ${room.id})`;
                            roomSelect.appendChild(opt);
                        });
                    }
                })
                .catch(err => {
                    roomSelect.innerHTML = '<option value="0">Error loading rooms</option>';
                });
        });
    }
}

//create update facility card function
function createUpdateFacilityCard() {
    const container = document.createElement("div");
    container.className = "container-fluid section_container";

    const card = document.createElement("div");
    card.className = "card card_container shadow-lg border-primary";

    const form = document.createElement("form");
    form.className = "d-flex submit_form";
    form.action = "../MainController"; // Đảm bảo đúng đường dẫn tới Controller
    form.method = "post";

    const formTitle = document.createElement("h5");
    formTitle.className = "text-center mt-3 text-primary fw-bold";
    formTitle.textContent = "Update Facility";

    const hiddenAction = document.createElement("input");
    hiddenAction.type = "hidden";
    hiddenAction.name = "action";

    const dynamicContainer = document.createElement("div");
    dynamicContainer.className = "dynamic_update_fields d-flex flex-column gap-3 mt-3";

    const buttonContainer = document.createElement("div");
    buttonContainer.className = "button_container mt-4 d-flex justify-content-center gap-4";

    const updateBtn = document.createElement("button");
    updateBtn.type = "submit";
    updateBtn.className = "btn btn-primary submit_update_facility_button px-4";
    updateBtn.textContent = "Save Changes";

    const cancelBtn = document.createElement("button");
    cancelBtn.type = "button";
    cancelBtn.className = "btn btn-outline-secondary cancel_update_facility_button px-4";
    cancelBtn.textContent = "Cancel";

    buttonContainer.appendChild(updateBtn);
    buttonContainer.appendChild(cancelBtn);

    form.append(formTitle, hiddenAction, dynamicContainer, buttonContainer);
    card.appendChild(form);
    container.appendChild(card);

    return { container, form, dynamicContainer, hiddenAction, cancelBtn };
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
    // Xác định xem mình đang bấm Update ở mục nào (Dựa vào Action button)
    const actionBtn = card.querySelector('button[name="action"]');
    const actionType = actionBtn ? actionBtn.value : "";

    updateUI.dynamicContainer.innerHTML = ""; // Clear form cũ

    if (actionType === "UpdateHome") {
        updateUI.hiddenAction.value = "UpdateHome";
        updateUI.dynamicContainer.innerHTML = `
            <input type="hidden" name="txtHomeId" value="${getHiddenValue(card, 'txtHomeId')}">
            <label class="fw-bold text-muted small mb-0">House Code</label>
            <input class="form-control" type="text" name="txtCode" value="${getHiddenValue(card, 'txtCode')}" required>
            <label class="fw-bold text-muted small mb-0 mt-2">House Name</label>
            <input class="form-control" type="text" name="txtFacilityName" value="${getHiddenValue(card, 'txtName')}" required>
            <label class="fw-bold text-muted small mb-0 mt-2">Address</label>
            <input class="form-control" type="text" name="txtAddress" value="${getHiddenValue(card, 'txtAddress')}">
            <label class="fw-bold text-muted small mb-0 mt-2">Owner User ID</label>
            <input class="form-control" type="number" name="txtOwnerId" value="${getHiddenValue(card, 'txtOwnerId') || ''}">
            <label class="fw-bold text-muted small mb-0 mt-2">Status</label>
            <select class="form-select" name="txtStatus">
                <option value="Active" ${getHiddenValue(card, 'txtStatus') === 'Active' ? 'selected' : ''}>Active</option>
                <option value="Inactive" ${getHiddenValue(card, 'txtStatus') === 'Inactive' ? 'selected' : ''}>Inactive</option>
                <option value="Maintenance" ${getHiddenValue(card, 'txtStatus') === 'Maintenance' ? 'selected' : ''}>Maintenance</option>
            </select>
        `;

    } else if (actionType === "UpdateRoom") {
        updateUI.hiddenAction.value = "UpdateRoom";
        updateUI.dynamicContainer.innerHTML = `
            <input type="hidden" name="txtRoomId" value="${getHiddenValue(card, 'txtRoomId')}">
            <label class="fw-bold text-muted small mb-0">Belonging House ID</label>
            <input class="form-control" type="number" name="txtHomeId" value="${getHiddenValue(card, 'txtHomeId')}" required>
            <label class="fw-bold text-muted small mb-0 mt-2">Room Name</label>
            <input class="form-control" type="text" name="txtFacilityName" value="${getHiddenValue(card, 'txtName')}" required>
            <label class="fw-bold text-muted small mb-0 mt-2">Floor</label>
            <input class="form-control" type="number" name="txtFloor" value="${getHiddenValue(card, 'txtFloor')}">
            <label class="fw-bold text-muted small mb-0 mt-2">Room Type</label>
            <select class="form-select" name="txtRoomType">
                <option value="LivingRoom" ${getHiddenValue(card, 'txtRoomType') === 'LivingRoom' ? 'selected' : ''}>Living Room</option>
                <option value="BedRoom" ${getHiddenValue(card, 'txtRoomType') === 'BedRoom' ? 'selected' : ''}>Bed Room</option>
                <option value="Kitchen" ${getHiddenValue(card, 'txtRoomType') === 'Kitchen' ? 'selected' : ''}>Kitchen</option>
                <option value="BathRoom" ${getHiddenValue(card, 'txtRoomType') === 'BathRoom' ? 'selected' : ''}>Bath Room</option>
            </select>
            <label class="fw-bold text-muted small mb-0 mt-2">Status</label>
            <select class="form-select" name="txtStatus">
                <option value="Active" ${getHiddenValue(card, 'txtStatus') === 'Active' ? 'selected' : ''}>Active</option>
                <option value="Inactive" ${getHiddenValue(card, 'txtStatus') === 'Inactive' ? 'selected' : ''}>Inactive</option>
            </select>
        `;

    } else if (actionType === "UpdateDevice") {
        updateUI.hiddenAction.value = "UpdateDevice";
        updateUI.dynamicContainer.innerHTML = `
            <input type="hidden" name="txtDeviceId" value="${getHiddenValue(card, 'txtDeviceId')}">
            
            <label class="fw-bold text-muted small mb-0">Device Name</label>
            <input class="form-control" type="text" name="txtFacilityName" value="${getHiddenValue(card, 'txtName')}" required>
            
            <div class="row mt-2">
                <div class="col-6">
                    <label class="fw-bold text-muted small mb-0">House ID</label>
                    <input class="form-control" type="number" name="txtHomeId" value="${getHiddenValue(card, 'txtHomeId') || ''}" required>
                </div>
                <div class="col-6">
                    <label class="fw-bold text-muted small mb-0">Room ID</label>
                    <input class="form-control" type="number" name="txtRoomId" value="${getHiddenValue(card, 'txtRoomId') || ''}">
                </div>
            </div>

            <div class="row mt-2">
                <div class="col-6">
                    <label class="fw-bold text-muted small mb-0">Serial No</label>
                    <input class="form-control" type="text" name="txtCode" value="${getHiddenValue(card, 'txtSerialNo')}" required>
                </div>
                <div class="col-6">
                    <label class="fw-bold text-muted small mb-0">Device Type</label>
                    <select class="form-select" name="txtDeviceType">
                        <option value="DoorSensor" ${getHiddenValue(card, 'txtDeviceType') === 'DoorSensor' ? 'selected' : ''}>Door Sensor</option>
                        <option value="SmartLock" ${getHiddenValue(card, 'txtDeviceType') === 'SmartLock' ? 'selected' : ''}>Smart Lock</option>
                        <option value="LightSwitch" ${getHiddenValue(card, 'txtDeviceType') === 'LightSwitch' ? 'selected' : ''}>Light Switch</option>
                        <option value="SmartLight" ${getHiddenValue(card, 'txtDeviceType') === 'SmartLight' ? 'selected' : ''}>Smart Light</option>
                    </select>
                </div>
            </div>

            <label class="fw-bold text-muted small mb-0 mt-2">Vendor</label>
            <input class="form-control" type="text" name="txtVendor" value="${getHiddenValue(card, 'txtVendor')}">
            
            <label class="fw-bold text-muted small mb-0 mt-2">Current State</label>
            <select class="form-select" name="txtStatus">
                <option value="On" ${getHiddenValue(card, 'txtStatus') === 'On' ? 'selected' : ''}>On</option>
                <option value="Off" ${getHiddenValue(card, 'txtStatus') === 'Off' ? 'selected' : ''}>Off</option>
                <option value="Opened" ${getHiddenValue(card, 'txtStatus') === 'Opened' ? 'selected' : ''}>Opened</option>
                <option value="Closed" ${getHiddenValue(card, 'txtStatus') === 'Closed' ? 'selected' : ''}>Closed</option>
                <option value="Locked" ${getHiddenValue(card, 'txtStatus') === 'Locked' ? 'selected' : ''}>Locked</option>
                <option value="Unlocked" ${getHiddenValue(card, 'txtStatus') === 'Unlocked' ? 'selected' : ''}>Unlocked</option>
            </select>
        `;
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