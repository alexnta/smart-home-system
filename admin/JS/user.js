
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

function createUserCard(e, selectRole) {
    e.preventDefault();

    let userCardContainer = document.createElement("div");
    userCardContainer.className = "col-6 col-lg-3 card_container";
    let userCard = document.createElement("div");
    userCard.className = "card user_card";
    let userCardBody = document.createElement("div");
    userCardBody.className = "card-body";
    let userCardTitle = document.createElement("h5");
    userCardTitle.className = "card-title";
    userCardTitle.textContent = document.getElementsByClassName("input_user_name")[0].value;
    let userCardRole = document.createElement("p");
    userCardRole.className = "card-text";
    userCardRole.textContent = selectRole.options[selectRole.selectedIndex].text;
    let houseIDText = document.createElement("p");
    houseIDText.className = "card-text";
    let houseID = document.getElementById("houseInput");
    if (houseID) {
        houseIDText.textContent = "House ID: " + houseID.value;
    }
    let userCardID = document.createElement("p");
    userCardID.className = "card-text";
    userCardID.textContent = "User ID: " + generateID(IDList);

    let updateUserButton = document.createElement("button");
    updateUserButton.className = "btn btn-primary update_user_button";
    updateUserButton.textContent = "Update";

    let deleteUserButton = document.createElement("button");
    deleteUserButton.className = "btn btn-danger delete_user_button mx-2";
    deleteUserButton.textContent = "Delete";

    let userContainerButons = document.createElement("div");
    userContainerButons.className = "user_card_button_container d-flex mt-1 mb-2 justify-content-center gap-3";
    userContainerButons.appendChild(updateUserButton);
    userContainerButons.appendChild(deleteUserButton);
    userCardBody.appendChild(userCardTitle);
    userCardBody.appendChild(userCardRole);
    userCardBody.appendChild(houseIDText);
    userCardBody.appendChild(userCardID);
    userCard.appendChild(userCardBody);
    userCardContainer.appendChild(userCard);
    userCard.appendChild(userContainerButons);
    document.getElementById("user_management_section").appendChild(userCardContainer);
    document.getElementById("create_user_form").reset();
    document.getElementById("houseInput")?.remove();
    // createUserCardContainer.remove();
    // overlay.remove();
}
function displayHouseInput(selectRole){
    const role = selectRole.value;
    let houseInput = document.getElementById("houseInput");

    if (role === "1" || role === "3") {
        if (!houseInput) {
            houseInput = document.createElement("input");
            houseInput.id = "houseInput";
            houseInput.className = "form-control mx-auto mt-3 input_data";
            houseInput.placeholder = "Enter House ID";

            document.getElementById("create_user_form").insertBefore(
                houseInput,
                document.getElementById("create_user_button_container")
            );
        }
    } else {
        houseInput?.remove();
    }
}

//create update user card function
function createUpdateUserCard() {
    const container = document.createElement("div");
    container.className = "container-fluid section_container";

    const card = document.createElement("div");
    card.className = "card card_container";

    const form = document.createElement("form");
    form.className = "d-flex submit_form";

    const formTitle = document.createElement("h5");
    formTitle.className = "text-center mt-3";
    formTitle.textContent = "Update User";

    const inputUserName = document.createElement("input");
    inputUserName.className = "form-control mx-auto mt-3 input_data";
    inputUserName.placeholder = "Enter name";

    const selectRole = document.createElement("select");
    selectRole.className = "form-select mx-auto mt-3 input_data";
    selectRole.innerHTML = `
        <option selected>Choose role</option>
        <option value="1">House owner</option>
        <option value="2">Technician</option>
        <option value="3">Viewer</option>
    `;

    const buttonContainer = document.createElement("div");
    buttonContainer.className = "button_container mt-3 d-flex justify-content-center gap-5";

    const updateBtn = document.createElement("button");
    updateBtn.type = "submit";
    updateBtn.className = "btn btn-outline-success submit_update_user_button";
    updateBtn.textContent = "Update";

        const cancelBtn = document.createElement("button");
    cancelBtn.type = "button";
    cancelBtn.className = "btn btn-outline-danger cancel_update_user_button";
    cancelBtn.textContent = "Cancel";

    buttonContainer.appendChild(updateBtn);
    buttonContainer.appendChild(cancelBtn);
    form.append(formTitle, inputUserName, selectRole, buttonContainer);
    card.appendChild(form);
    container.appendChild(card);

    return {
        container,
        form,
        inputUserName,
        selectRole,
        buttonContainer
    };
}

function displayUpdateUserCard(e, updateUI, overlay) {
    e.preventDefault();

    const card = e.target.closest(".user_card");
    const body = card.querySelector(".card-body");

    updateUI.inputUserName.value =
        body.querySelector(".card-title").textContent;

    const roleText = body.querySelectorAll(".card-text")[0].textContent;

    [...updateUI.selectRole.options].forEach(opt => {
        opt.selected = opt.text === roleText;
    });

    overlay.innerHTML = "";
    overlay.appendChild(updateUI.container);
    document.body.appendChild(overlay);

    return card;
}



function updateUserData(e, card, overlay, updateUI) {
    e.preventDefault();
    if (!card) return;

    const body = card.querySelector(".card-body");

    body.querySelector(".card-title").textContent =
        updateUI.inputUserName.value;

    body.querySelectorAll(".card-text")[0].textContent =
        updateUI.selectRole.options[updateUI.selectRole.selectedIndex].text;

    overlay.remove();
}


function showHouseIDInputUpdate(updateUI) {
    const houseInput = document.createElement("input");
    houseInput.className = "form-control mx-auto mt-3 input_data";
    houseInput.placeholder = "Enter House ID";

    updateUI.selectRole.addEventListener("change", () => {
        const role = updateUI.selectRole.value;
        const existed = houseInput.isConnected;

        if ((role === "1" || role === "3") && !existed) {
            updateUI.form.insertBefore(houseInput, updateUI.buttonContainer);
        } else if (!(role === "1" || role === "3") && existed) {
            houseInput.remove();
        }
    });
}


export{
    createUserCard,
    displayHouseInput,
    createUpdateUserCard,
    displayUpdateUserCard,
    updateUserData,
    showHouseIDInputUpdate
}