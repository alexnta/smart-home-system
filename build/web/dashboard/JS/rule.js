function createUpdateRuleCard() {
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
    formTitle.textContent = "Update Rule";

    const hiddenAction = document.createElement("input");
    hiddenAction.type = "hidden";
    hiddenAction.name = "action";
    hiddenAction.value = "UpdateRule";

const inputRuleName = document.createElement("input");
inputRuleName.className = "form-control mx-auto mt-3 input_data";
inputRuleName.name = "ruleName";
inputRuleName.placeholder = "Enter full name";

const inputHomeId = document.createElement("input");
inputHomeId.className = "form-control mx-auto mt-3 input_data";
inputHomeId.name = "homeId";
inputHomeId.placeholder = "Enter home id";

const selectOperator = document.createElement("select");
selectOperator.className = "form-select mx-auto mt-3 input_data";
selectOperator.name = "operator";
selectOperator.innerHTML = `
    <option value="">Choose role</option>
    <option value="1">></option>
    <option value="2"><</option>
    <option value="3">>=</option>
    <option value="4"><=</option>
    <option value="5">==</option>
`;
    
    const selectTriggerType = document.createElement("select");
selectTriggerType.className = "form-select mx-auto mt-3 input_data";
selectTriggerType.name = "triggerType";
selectTriggerType.innerHTML = `
    <option value="">Choose trigger type</option>
    <option value="1">Event</option>
    <option value="2">Schedule</option>
    <option value="3">Threshold</option>

`;

    const selectSeverityType = document.createElement("select");
selectSeverityType.className = "form-select mx-auto mt-3 input_data";
selectSeverityType.name = "triggerType";
selectSeverityType.innerHTML = `
    <option value="">Choose trigger type</option>
    <option value="1">Info</option>
    <option value="2">Warning</option>
    <option value="3">Critical</option>

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

    form.append(
        hiddenAction,
        formTitle,
        inputRuleName,
        inputHomeId,
        selectOperator,
        selectTriggerType,
        selectSeverityType,
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

function displayUpdateRuleCard(e, updateUI, overlay) {
    e.preventDefault();

    const formSource = e.target.closest("form");
    if (!formSource) return null;

    // lấy dữ liệu từ form hidden trong card JSP
    updateUI.inputRuleName.value = getHiddenValue(formSource, "ruleName");
    updateUI.inputHomeId.value = getHiddenValue(formSource, "homeId");

    const triggerTypeValue = getHiddenValue(formSource, "triggerType");
    [...updateUI.selectTriggerType.options].forEach(opt => {
        opt.selected = opt.value === triggerTypeValue;
    });

    const operatorValue = getHiddenValue(formSource, "operator");
    [...updateUI.selectOperator.options].forEach(opt => {
        opt.selected = opt.value === operatorValue;
    });

    const severityValue = getHiddenValue(formSource, "severity");
    [...updateUI.selectSeverityType.options].forEach(opt => {
        opt.selected = opt.value === severityValue;
    });

    overlay.innerHTML = "";
    overlay.appendChild(updateUI.container);
    document.body.appendChild(overlay);

    return formSource;
}
export {
getHiddenValue,
    createUpdateRuleCard,
    displayUpdateRuleCard
}
