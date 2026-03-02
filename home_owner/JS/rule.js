function createRuleTemplateCard() {
    const ruleCardContainer = document.createElement("div");
    ruleCardContainer.className = "col-6 col-lg-3 card_container";
    ruleCardContainer.id = "rule_list_container";

    const ruleCard = document.createElement("div");
    ruleCard.className = "rule_card card";
    ruleCard.id = "rule_card";

    const titleText = document.createElement("h5");
    titleText.style = "text-align: center;";
    let deviceIDText = document.getElementsByClassName("input_device_id")[0].value
    titleText.innerHTML = `
    Device: <span class="device_id_value card-title text-center mt-2">${deviceIDText}</span>
`;

    const ruleTemplateType = document.createElement("p");
    ruleTemplateType.className = "card-text text-center";
    ruleTemplateType.textContent = `Rule: ${document.getElementById("rule_template_options_select").options[document.getElementById("rule_template_options_select").selectedIndex].text}`;

    const updateRuleButton = document.createElement("button");
    updateRuleButton.className = "btn btn-primary update_rule_button";
    updateRuleButton.textContent = "Update";

    const deleteRuleButton = document.createElement("button");
    deleteRuleButton.className = "btn btn-danger delete_rule_button";
    deleteRuleButton.textContent = "Delete";

    let ruleButtonContainer = document.createElement("div");
    ruleButtonContainer.className = "rule_card_button_container d-flex mt-1 mb-2 justify-content-center gap-3";
    ruleButtonContainer.appendChild(updateRuleButton);
    ruleButtonContainer.appendChild(deleteRuleButton);

    ruleCard.appendChild(titleText);
    ruleCard.appendChild(ruleTemplateType);

    ruleCard.appendChild(ruleButtonContainer);
    ruleCardContainer.appendChild(ruleCard);

    document.getElementById("manage_rule_section").appendChild(ruleCardContainer);
    document.getElementById("create_rule_form").reset();

}

function createUpdateTemplateCard() {
    const container = document.createElement("div");
    container.className = "container-fluid section_container";

    const card = document.createElement("div");
    card.className = "card card_container";

    const form = document.createElement("form");
    form.className = "d-flex submit_form";

    const formTitle = document.createElement("h5");
    formTitle.className = "text-center mt-3";
    formTitle.textContent = "Update Rule";

    const inputDeviceID = document.createElement("input");
    inputDeviceID.className = "form-control mx-auto mt-3 input_data";
    inputDeviceID.placeholder = "Enter rule ID";

    const selectTemplate = document.createElement("select");
    selectTemplate.id = "update_rule_template_options_select";
    selectTemplate.className = "form-select mx-auto mt-3 input_data";
    selectTemplate.innerHTML = `
        <option selected>Choose type</option>
        <option value="1">Event</option>
        <option value="2">Schedule</option>
        <option value="3">Threshold</option>
    `;

    const buttonContainer = document.createElement("div");
    buttonContainer.className = "button_container mt-3 d-flex justify-content-center gap-5";

    const updateBtn = document.createElement("button");
    updateBtn.type = "submit";
    updateBtn.className = "btn btn-outline-success submit_update_rule_button";
    updateBtn.textContent = "Update";

    const cancelBtn = document.createElement("button");
    cancelBtn.type = "button";
    cancelBtn.className = "btn btn-outline-danger cancel_update_rule_button";
    cancelBtn.textContent = "Cancel";

    buttonContainer.appendChild(updateBtn);
    buttonContainer.appendChild(cancelBtn);
    form.append(formTitle, inputDeviceID, selectTemplate, buttonContainer);
    card.appendChild(form);
    container.appendChild(card);

    return {
        container,
        form,
        inputDeviceID,
        selectTemplate,
        buttonContainer
    };
}
function displayUpdateRuleCard(e, updateUI, overlay) {
    e.preventDefault();

    const card = e.target.closest(".rule_card");
    const body = card;

    updateUI.inputDeviceID.value =
        body.querySelector(".device_id_value").textContent;

    const templateText = body.querySelector(".card-text").textContent;
    //const severityText = body.querySelectorAll(".card-text")[1].textContent;

    [...updateUI.selectTemplate.options].forEach(opt => {
        opt.selected = templateText.includes(opt.text);
    });


    overlay.innerHTML = "";
    overlay.appendChild(updateUI.container);
    document.body.appendChild(overlay);

    return card;
}

function updateRuleData(e, card, overlay, updateUI) {
    e.preventDefault();
    if (!card) return;

    const body = card;

    // update name
    body.querySelector(".card-title").textContent =
        updateUI.inputDeviceID.value;

    // update rule type
    const templateText = body.querySelector(".card-text");
const selectedOption =
    updateUI.selectTemplate.options[updateUI.selectTemplate.selectedIndex];

    templateText.textContent = `Rule : ${selectedOption.text}`;

    overlay.remove();
}



export {
createRuleTemplateCard,
createUpdateTemplateCard,
    displayUpdateRuleCard,
    updateRuleData,

}
