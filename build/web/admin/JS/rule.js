// 1. TẠO GIAO DIỆN UPDATE RULE 
function createUpdateRuleCard() {
    const container = document.createElement("div");
    container.className = "container-fluid section_container";

    const card = document.createElement("div");
    card.className = "card card_container shadow-lg border-warning";
    card.style.width = "600px";

    const form = document.createElement("form");
    form.className = "d-flex submit_form";
    form.action = "../MainController";
    form.method = "post";

    const formTitle = document.createElement("h5");
    formTitle.className = "text-center mt-3 text-warning fw-bold";
    formTitle.textContent = "Update Rule Template";

    const hiddenAction = document.createElement("input");
    hiddenAction.type = "hidden";
    hiddenAction.name = "action";
    hiddenAction.value = "UpdateRule";
    
    const hiddenRuleId = document.createElement("input");
    hiddenRuleId.type = "hidden";
    hiddenRuleId.name = "ruleId";

    const inputHomeId = document.createElement("input");
    inputHomeId.type = "hidden";
    inputHomeId.name = "homeId";

    // Vùng chứa nội dung động
    const dynamicContainer = document.createElement("div");
    dynamicContainer.className = "d-flex flex-column gap-2 mt-3";

    dynamicContainer.innerHTML = `
        <label class="fw-bold text-muted small mb-0">Rule Name</label>
        <input class="form-control input_data w-100" name="ruleName" required>

        <div class="row">
            <div class="col-6">
                <label class="fw-bold text-muted small mb-0">Trigger Type</label>
                <select class="form-select input_data w-100" name="triggerType" required>
                    <option value="Event">Event</option>
                    <option value="Schedule">Schedule</option>
                    <option value="Threshold">Threshold</option>
                </select>
            </div>
            <div class="col-6">
                <label class="fw-bold text-muted small mb-0">Severity</label>
                <select class="form-select input_data w-100" name="severity" required>
                    <option value="Info">Info</option>
                    <option value="Warning">Warning</option>
                    <option value="Critical">Critical</option>
                </select>
            </div>
        </div>

        <label class="fw-bold text-muted small mb-0 mt-2">Condition (JSON Format)</label>
        <textarea class="form-control input_data w-100 font-monospace" rows="4" name="conditionjson" id="json_update_input" required></textarea>
        <small id="json_update_error" class="text-danger fw-bold d-none">Invalid JSON format!</small>
    `;

    const buttonContainer = document.createElement("div");
    buttonContainer.className = "button_container mt-4 d-flex justify-content-center gap-4";

    const updateBtn = document.createElement("button");
    updateBtn.type = "submit";
    updateBtn.className = "btn btn-primary submit_update_rule_button px-4";
    updateBtn.textContent = "Save Changes";

    const cancelBtn = document.createElement("button");
    cancelBtn.type = "button";
    cancelBtn.className = "btn btn-outline-secondary cancel_update_rule_button px-4";
    cancelBtn.textContent = "Cancel";

    buttonContainer.appendChild(updateBtn);
    buttonContainer.appendChild(cancelBtn);

    form.append(hiddenAction, hiddenRuleId, inputHomeId, formTitle, dynamicContainer, buttonContainer);
    card.appendChild(form);
    container.appendChild(card);

    return {
        container,
        form,
        hiddenRuleId,
        inputHomeId,
        inputRuleName: dynamicContainer.querySelector('input[name="ruleName"]'),
        selectTriggerType: dynamicContainer.querySelector('select[name="triggerType"]'),
        selectSeverityType: dynamicContainer.querySelector('select[name="severity"]'),
        inputCondition: dynamicContainer.querySelector('textarea[name="conditionjson"]'),
        jsonErrorText: dynamicContainer.querySelector('#json_update_error'),
        cancelBtn
    };
}

function getHiddenValue(formSource, name) {
    const input = formSource.querySelector(`input[name="${name}"]`);
    return input ? input.value : "";
}

// 2. ĐỔ DỮ LIỆU CŨ VÀO FORM UPDATE
function displayUpdateRuleCard(e, updateUI, overlay) {
    const formSource = e.target.closest("form");
    if (!formSource) return null;

    // Gắn ID
    updateUI.hiddenRuleId.value = getHiddenValue(formSource, "ruleId");
    updateUI.inputHomeId.value = getHiddenValue(formSource, "homeId");
    
    // Gắn Text
    updateUI.inputRuleName.value = getHiddenValue(formSource, "ruleName");
    updateUI.inputCondition.value = getHiddenValue(formSource, "conditionjson");

    // Gắn Select Dropdown
    updateUI.selectTriggerType.value = getHiddenValue(formSource, "triggerType");
    updateUI.selectSeverityType.value = getHiddenValue(formSource, "severity");

    // Ẩn lỗi JSON nếu có từ lần bấm trước
    updateUI.jsonErrorText.classList.add("d-none");

    overlay.innerHTML = "";
    overlay.appendChild(updateUI.container);
    document.body.appendChild(overlay);

    // KÍCH HOẠT VALIDATE JSON KHI SUBMIT FORM UPDATE
    updateUI.form.addEventListener('submit', function(event) {
        try {
            JSON.parse(updateUI.inputCondition.value); // Thử parse JSON
            updateUI.jsonErrorText.classList.add("d-none"); // Nếu đúng -> ẩn lỗi đi tiếp
        } catch (err) {
            event.preventDefault(); // Nếu sai -> Chặn form submit
            updateUI.jsonErrorText.classList.remove("d-none"); // Hiện chữ đỏ báo lỗi
        }
    });

    return formSource;
}

// 3. KÍCH HOẠT VALIDATE JSON CHO FORM CREATE (Hàm này nên được gọi trong main.js)
function initJsonValidatorForCreate() {
    const createForm = document.getElementById("create_rule_form");
    const jsonInput = document.getElementById("json_create_input");
    const jsonError = document.getElementById("json_create_error");

    if(createForm && jsonInput) {
        createForm.addEventListener('submit', function(event) {
            try {
                JSON.parse(jsonInput.value); // Thử parse
                jsonError.classList.add("d-none");
            } catch (err) {
                event.preventDefault(); // Chặn
                jsonError.classList.remove("d-none");
            }
        });
    }
}

export {
getHiddenValue,
    createUpdateRuleCard,
    displayUpdateRuleCard,
    initJsonValidatorForCreate
}
