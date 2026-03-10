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
    
    const hiddenRuleId = document.createElement("input");
hiddenRuleId.type = "hidden";
hiddenRuleId.name = "ruleId";

const inputRuleName = document.createElement("input");
inputRuleName.className = "form-control mx-auto mt-3 input_data";
inputRuleName.name = "ruleName";
inputRuleName.placeholder = "Enter full name";

const inputHomeId = document.createElement("input");
inputHomeId.className = "form-control mx-auto mt-3 input_data";
inputHomeId.name = "homeId";
inputHomeId.placeholder = "Enter home id";

const inputCondition = document.createElement("input");
inputCondition.className = "form-control mx-auto mt-3 input_data";
inputCondition.name = "conditionjson";
inputCondition.placeHolder = "Enter condition";
    
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
selectSeverityType.name = "severity";
selectSeverityType.innerHTML = `
    <option value="">Choose severity</option>
    <option value="1">Info</option>
    <option value="2">Warning</option>
    <option value="3">Critical</option>

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

    form.append(
        hiddenAction,
hiddenRuleId,
        formTitle,
        inputRuleName,
        inputHomeId,
        inputCondition,
        selectTriggerType,
        selectSeverityType,
        buttonContainer
    );

    card.appendChild(form);
    container.appendChild(card);

    return {
        container,
        form,
        inputRuleName,
        hiddenRuleId,
        inputHomeId,
        inputCondition,
        selectTriggerType,
        selectSeverityType,
        buttonContainer,
        cancelBtn
    };
}

function getHiddenValue(formSource, name) {
    const input = formSource.querySelector(`input[name="${name}"]`);
    return input ? input.value : "";
}

function displayUpdateRuleCard(e, updateUI, overlay) {


    const formSource = e.target.closest("form");
    if (!formSource) return null;

    // lấy dữ liệu từ form hidden trong card JSP
    updateUI.hiddenRuleId.value = getHiddenValue(formSource, "ruleId");
    updateUI.inputRuleName.value = getHiddenValue(formSource, "ruleName");
    updateUI.inputHomeId.value = getHiddenValue(formSource, "homeId");

    const selectTriggerType = getHiddenValue(formSource, "triggerType");
    [...updateUI.selectTriggerType.options].forEach(opt => {
        opt.selected = opt.value === selectTriggerType;
    });

    updateUI.inputCondition.value = getHiddenValue(formSource, "conditionjson");

    const selectSeverityType = getHiddenValue(formSource, "severity");
    [...updateUI.selectSeverityType.options].forEach(opt => {
        opt.selected = opt.value === selectSeverityType;
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
