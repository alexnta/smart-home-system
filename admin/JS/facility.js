
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
    facilityCardTitle.textContent = document.getElementsByClassName("input_facility_name")[0].value;
    let facilityCardType = document.createElement("p");
    facilityCardType.className = "card-text";
    facilityCardType.textContent = selectFacilityType.options[selectFacilityType.selectedIndex].text;
    let facilityIDText = document.createElement("p");
    facilityIDText.className = "card-text";

//EDIT THIS AREA
    let belongToInputID = document.getElementById("belongToInput");

    let facilityCardID = document.createElement("p");
    facilityCardID.className = "card-text";
    facilityCardID.textContent = "Facility ID: " + generateID(IDList);

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
    facilityCardBody.appendChild(facilityIDText);
    facilityCardBody.appendChild(facilityCardID);
    facilityCard.appendChild(facilityCardBody);
    facilityCardContainer.appendChild(facilityCard);
    facilityCard.appendChild(facilityContainerButtons);
    //test
    switch(facilityCardType.textContent){
        case "House":
            document.getElementById("house_management_section").appendChild(facilityCardContainer);
            break;
        case "Room":
            // let belongToHouseIDTexrt = document.createElement("p");
            // belongToHouseIDTexrt.className = "card-text";
            // belongToHouseIDTexrt.textContent = "Belong to house: " + belongToInputID.value;
            // facilityCardBody.insertBefore(belongToHouseIDTexrt, facilityCardID);
            facilityIDText.textContent = "Belong to house : " + belongToInputID.value;
            document.getElementById("room_management_section").appendChild(facilityCardContainer);
            break;
        case "Device":
            // let belongToRoomIDTexrt = document.createElement("p");
            // belongToRoomIDTexrt.className = "card-text";
            // belongToRoomIDTexrt.textContent = "Belong to room: " + belongToInputID.value;
            // facilityCardBody.insertBefore(belongToRoomIDTexrt, facilityCardID);
            facilityIDText.textContent = "Belong to room : " + belongToInputID.value;
            document.getElementById("device_management_section").appendChild(facilityCardContainer);
            break;
    }

    document.getElementById("create_facility_form").reset();
    document.getElementById("belongToInput")?.remove();
    // createUserCardContainer.remove();
    // overlay.remove();
}
function displayBelongToInput(selectFacilityType){
    const type = selectFacilityType.value;
    let belongToInput = document.getElementById("belongToInput");

    if (type === "2") {
        if (!belongToInput) {
            belongToInput = document.createElement("input");
            belongToInput.setAttribute("id", "belongToInput");
            belongToInput.className = "form-control mx-auto mt-3 input_data";
            belongToInput.placeholder = "Enter House ID";

            document.getElementById("create_facility_form").insertBefore(
                belongToInput,
                document.getElementById("create_facility_button_container")
            );
        }
    } else if(type == "3"){
        if (!belongToInput) {
            belongToInput = document.createElement("input");
            belongToInput.id = "belongToInput";
            belongToInput.className = "form-control mx-auto mt-3 input_data";
            belongToInput.placeholder = "Enter Room ID";

            document.getElementById("create_facility_form").insertBefore(
                belongToInput,
                document.getElementById("create_facility_button_container")
            );
        }
    } else {
        belongToInput?.remove();
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

    const formTitle = document.createElement("h5");
    formTitle.className = "text-center mt-3";
    formTitle.textContent = "Update Facility";

    const inputFacilityName = document.createElement("input");
    inputFacilityName.className = "form-control mx-auto mt-3 input_data";
    inputFacilityName.placeholder = "Enter facility name";

    const selectType = document.createElement("select");
    selectType.className = "form-select mx-auto mt-3 input_data";
    selectType.innerHTML = `
        <option selected>Choose type</option>
        <option value="1">House</option>
        <option value="2">Room</option>
        <option value="3">Device</option>
    `;

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
    form.append(formTitle, inputFacilityName, selectType, buttonContainer);
    card.appendChild(form);
    container.appendChild(card);

    return {
        container,
        form,
        inputFacilityName,
        selectType,
        buttonContainer
    };
}

function displayUpdateFacilityCard(e, updateUI, overlay) {
    e.preventDefault();

    const card = e.target.closest(".facility_card");
    console.log(card);
    const body = card.querySelector(".card-body");

    updateUI.inputFacilityName.value =
        body.querySelector(".card-title").textContent;

    const typeText = body.querySelectorAll(".card-text")[0].textContent;

    [...updateUI.selectType.options].forEach(opt => {
        opt.selected = opt.text === typeText;
    });

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
        updateUI.inputFacilityName.value;

    // 2. Update type text
    const typeTextEl = body.querySelectorAll(".card-text")[0];
    const selectedOption = updateUI.selectType.options[updateUI.selectType.selectedIndex];
    typeTextEl.textContent = selectedOption.text;

    const typeValue = selectedOption.value;

    // 3. Tìm "Belong To" hiện có (nếu có)
    let belongToEl = [...body.querySelectorAll(".card-text")]
        .find(p => p.textContent.startsWith("Belong"));

    // 4. Lấy giá trị input belong (nếu có)
    const belongInput = updateUI.form.querySelector("#belongToInput");
    console.log(belongInput);
    const belongValue = belongInput?.value;

    // ===== CASE LOGIC =====

    if (typeValue === "1") {
        // HOUSE → không có belong
        belongToEl?.remove();
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
            updateUI.form.insertBefore(belongToInput, updateUI.buttonContainer);
        } else if (!(role === "1" || role === "3") && existed) {
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