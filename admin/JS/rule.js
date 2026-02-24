let IDList = [];
function generateID(arr, min = 1, max = 100) {
    let randomNumber = Math.floor(Math.random() * (max - min + 1)) + min;
    while (arr.includes(randomNumber)) {
        randomNumber++;
    }
    arr.push(randomNumber);
    return randomNumber;
}

function changeRuleType() {
    const rule = document.getElementById("rule_options_select").value;
    const dynamicContainer = document.getElementById("rule_options_container");
    dynamicContainer.innerHTML = "";
    switch (rule) {

        case "2":

            let dayContainer = document.createElement("div");
            dayContainer.className = "d-flex justify-content-center mb-0";
            dayContainer.id = "day_container";
            let daySelect = document.createElement("select");
            daySelect.className = "form-select mx-auto mt-3 mb-0 input_data input_day_schedule";
            daySelect.id = "day_schedule_select";
            daySelect.innerHTML = `
            <option class="day_options" selected>Choose day</option>
            <option value="Monday">Monday</option>
            <option value="Tuesday">Tuesday</option>
            <option value="Wednesday">Wednesday</option>
            <option value="Thursday">Thursday</option>
            <option value="Friday">Friday</option>
            <option value="Saturday">Saturday</option>
            <option value="Sunday">Sunday</option>
            `;
            dayContainer.appendChild(daySelect);
            //create time input
            let scheduleTimeContainer = document.createElement("div");
            scheduleTimeContainer.className = "d-flex justify-content-center flex-column mt-3 mb-0";
            scheduleTimeContainer.id = "schedule_time_container";

            let fromContainer = document.createElement("div");
            fromContainer.className = "row mb-3 align-items-center";
            fromContainer.id = "from_container";
            fromContainer.innerHTML = `
            <label class="col-3 col-form-label text-end">From:</label>
                  <div class="col-9">
    <input type="time" class="form-control input_data input_time_schedule" id="time_from_input">
  </div>
            
            `;
            let toContainer = document.createElement("div");
            toContainer.className = "row align-items-center";
            toContainer.id = "to_container";
            toContainer.innerHTML = `
            <label class="col-3 col-form-label text-end">To:</label>
            <div class="col-9">
            <input class="form-control input_data input_time_schedule" type="time" id="time_to_input" placeholder="To">
            </div>
            `;

            dynamicContainer.appendChild(fromContainer);
            dynamicContainer.appendChild(toContainer);
            dynamicContainer.appendChild(scheduleTimeContainer);
            dynamicContainer.appendChild(dayContainer);


            break;
        case "3":
            let durationInput = document.createElement("input");
            durationInput.type = "number";
            durationInput.className = "form-control mx-auto mt-3 mb-0 input_data input_duration_threshold";
            durationInput.id = "duration_threshold_input";
            durationInput.placeholder = "Duration (minutes)";

            dynamicContainer.appendChild(durationInput);
 
            document.getElementById("create_rule_form").insertBefore(dynamicContainer, document.getElementById("create_rule_button_container"));
            break;
        default:
            console.log("No rule type selected");
    }

}

function createRuleCard() {
    const ruleListContainer = document.createElement("div");
    ruleListContainer.className = "col-6 col-lg-3 card_container";
    ruleListContainer.id = "rule_list_container";

    const ruleCard = document.createElement("div");
    ruleCard.className = "rule_card card";
    ruleCard.id = "rule_card";

    const titleText = document.createElement("h5");
    titleText.className = "card-title text-center mt-2";
    titleText.textContent = document.getElementsByClassName("input_rule_name")[0].value;

    const conditionTypeText = document.createElement("p");
    conditionTypeText.className = "card-text text-center";
    conditionTypeText.textContent = `Condition: ${document.getElementById("condition_options_select").options[document.getElementById("condition_options_select").selectedIndex].text}`;

    const ruleTypeText = document.createElement("p");
    ruleTypeText.className = "card-text text-center";
    ruleTypeText.textContent = `Rule type: ${document.getElementById("rule_options_select").options[document.getElementById("rule_options_select").selectedIndex].text}`;

    const severityTypeText = document.createElement("p");
    severityTypeText.className = "card-text text-center";
    severityTypeText.textContent = `Severity: ${document.getElementById("severity_options_select").options[document.getElementById("severity_options_select").selectedIndex].text}`;

    const ruleID = document.createElement("p");
    ruleID.className = "card-text text-center";
    ruleID.textContent = `Rule ID: ${generateID(IDList)}`;

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
    ruleCard.appendChild(ruleID);
    ruleCard.appendChild(ruleTypeText);
    ruleCard.appendChild(severityTypeText);
    ruleCard.appendChild(conditionTypeText);

    let dynamicContainerCard = document.createElement("div");
    dynamicContainerCard.id = "dynamic_container_card";

    switch (ruleTypeText.textContent) {
        case "Rule type: Schedule":
            const daySchedule = document.createElement("p");
            daySchedule.className = "card-text text-center";
            daySchedule.textContent = `Day: ${document.getElementById("day_schedule_select").value}`;

            const timeFrom = document.createElement("p");
            timeFrom.className = "card-text text-center";
            timeFrom.textContent = `Time from: ${document.getElementById("time_from_input").value}`;

            const timeTo = document.createElement("p");
            timeTo.className = "card-text text-center";
            timeTo.textContent = `Time to: ${document.getElementById("time_to_input").value}`;


            dynamicContainerCard.appendChild(daySchedule);
            dynamicContainerCard.appendChild(timeFrom);
            dynamicContainerCard.appendChild(timeTo);
            ruleCard.appendChild(dynamicContainerCard);
            break;
        case "Rule type: Threshold":
            const stateThreshold = document.createElement("p");
            stateThreshold.className = "card-text text-center";
            stateThreshold.textContent = `State: ${document.getElementById("state_threshold_select").value}`;

            const durationThreshold = document.createElement("p");
            durationThreshold.className = "card-text text-center";
            durationThreshold.textContent = `Duration: ${document.getElementById("duration_threshold_input").value} minutes`;

            dynamicContainerCard.appendChild(stateThreshold);
            dynamicContainerCard.appendChild(durationThreshold);
            ruleCard.appendChild(dynamicContainerCard);
            break;
        case "Rule type: Event":
            ruleCard.appendChild(dynamicContainerCard);
            break;
        default:
            console.log("No rule type selected");
    }
    ruleCard.appendChild(ruleButtonContainer);
    ruleListContainer.appendChild(ruleCard);

    document.getElementById("edit_rule_section").appendChild(ruleListContainer);
    document.getElementById("create_rule_form").reset();
    document.getElementById("rule_options_container").innerHTML = "";

}

function createUpdateRuleCard() {
    const container = document.createElement("div");
    container.className = "container-fluid section_container";

    const card = document.createElement("div");
    card.className = "card card_container";

    const form = document.createElement("form");
    form.className = "d-flex submit_form";

    const formTitle = document.createElement("h5");
    formTitle.className = "text-center mt-3";
    formTitle.textContent = "Update Rule";

    const inputRuleName = document.createElement("input");
    inputRuleName.className = "form-control mx-auto mt-3 input_data";
    inputRuleName.placeholder = "Enter rule name";

    const selectType = document.createElement("select");
    selectType.id = "update_rule_type_select";
    selectType.className = "form-select mx-auto mt-3 input_data";
    selectType.innerHTML = `
        <option selected>Choose type</option>
        <option value="1">Event</option>
        <option value="2">Schedule</option>
        <option value="3">Threshold</option>
    `;

        const selectCondition = document.createElement("select");
    selectCondition.id = "update_rule_condition_select";
    selectCondition.className = "form-select mx-auto mt-3 input_data";
    selectCondition.innerHTML = `
        <option selected>Choose condition</option>
        <option value="1">On</option>
        <option value="2">Off</option>
    `;

    const severitySelect = document.createElement("select");
    severitySelect.id = "update_rule_severity_select";
    severitySelect.className = "form-select mx-auto mt-3 input_data";
    severitySelect.innerHTML = `
        <option selected>Choose severity</option>
        <option value="1">Info</option>
        <option value="2">Warning</option>
        <option value="3">Critical</option>
    `

    const dynamicUpdateContainer = document.createElement("div");
    dynamicUpdateContainer.id = "rule_update_options_container";
    dynamicUpdateContainer.className = "d-flex justify-content-center flex-column mt-3 mb-0";

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
    form.append(formTitle, inputRuleName, selectType, selectCondition, severitySelect, dynamicUpdateContainer, buttonContainer);
    card.appendChild(form);
    container.appendChild(card);

    return {
        container,
        form,
        inputRuleName,
        selectType,
        severitySelect,
        buttonContainer
    };
}
function displayUpdateRuleCard(e, updateUI, overlay) {
    e.preventDefault();

    const card = e.target.closest(".rule_card");
    const body = card;

    updateUI.inputRuleName.value =
        body.querySelector(".card-title").textContent;

    const typeText = body.querySelector(".card-text").textContent;
    //const severityText = body.querySelectorAll(".card-text")[1].textContent;

    [...updateUI.selectType.options].forEach(opt => {
        opt.selected = typeText.includes(opt.text);
    });

    // [...updateUI.severitySelect.options].forEach(opt => {
    //     opt.selected = severityText.includes(opt.text);
    // });

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
        updateUI.inputRuleName.value;

    // update rule type
    const typeTextEl = body.querySelector(".card-text");
    const selectedOption =
        updateUI.selectType.options[updateUI.selectType.selectedIndex];

    typeTextEl.textContent = `Rule type: ${selectedOption.text}`;

    let dynamicContainerCard = body.querySelector("#dynamic_container_card");
    if (selectedOption.value === "2") {
        dynamicContainerCard.innerHTML = "";
        const dayScheduleUpdate =
            document.createElement("p");
        dayScheduleUpdate.className = "card-text text-center";
        dayScheduleUpdate.textContent = `Day: ${document.getElementById("day_schedule_update_select").value}`;
        const timeFromUpdate = document.createElement("p");
        timeFromUpdate.className = "card-text text-center";
        timeFromUpdate.textContent = `Time from: ${document.getElementById("time_from_update_input").value}`;
        const timeToUpdate = document.createElement("p");
        timeToUpdate.className = "card-text text-center";
        timeToUpdate.textContent = `Time to: ${document.getElementById("time_to_update_input").value}`;
        dynamicContainerCard.appendChild(dayScheduleUpdate);
        dynamicContainerCard.appendChild(timeFromUpdate);
        dynamicContainerCard.appendChild(timeToUpdate);

    } else if (selectedOption.value === "3") {
        dynamicContainerCard.innerHTML = "";
        const stateThresholdUpdate = document.createElement("p");
        stateThresholdUpdate.className = "card-text text-center";
        stateThresholdUpdate.textContent = `State: ${document.getElementById("state_threshold_update_select").value}`;
        const durationThresholdUpdate = document.createElement("p");
        durationThresholdUpdate.className = "card-text text-center";
        durationThresholdUpdate.textContent = `Duration: ${document.getElementById("duration_threshold_update_input").value} minutes`;
        dynamicContainerCard.appendChild(stateThresholdUpdate);
        dynamicContainerCard.appendChild(durationThresholdUpdate);

    } else if (selectedOption.value === "1") {
        dynamicContainerCard.innerHTML = "";
    }

    overlay.remove();
}

function changeRuleTypeUpdate() {
    const ruleUpdate = document.getElementById("update_rule_type_select").value;
    const dynamicUpdateContainer = document.getElementById("rule_update_options_container");


    dynamicUpdateContainer.innerHTML = "";
    switch (ruleUpdate) {

        case "2":

            let dayUpdateContainer = document.createElement("div");
            dayUpdateContainer.className = "d-flex justify-content-center mb-0";
            dayUpdateContainer.id = "dayUpdateContainer";
            let dayUpdateSelect = document.createElement("select");
            dayUpdateSelect.className = "form-select mx-auto mt-3 mb-0 input_data input_day_schedule";
            dayUpdateSelect.id = "day_schedule_update_select";
            dayUpdateSelect.innerHTML = `
            <option class="day_update_options" selected>Choose day</option>
            <option value="Monday">Monday</option>
            <option value="Tuesday">Tuesday</option>
            <option value="Wednesday">Wednesday</option>
            <option value="Thursday">Thursday</option>
            <option value="Friday">Friday</option>
            <option value="Saturday">Saturday</option>
            <option value="Sunday">Sunday</option>
            `;
            dayUpdateContainer.appendChild(dayUpdateSelect);
            //create time input
            let scheduleTimeUpdateContainer = document.createElement("div");
            scheduleTimeUpdateContainer.className = "d-flex justify-content-center flex-column mb-0";
            scheduleTimeUpdateContainer.id = "schedule_time_update_container";

            let fromUpdateContainer = document.createElement("div");
            fromUpdateContainer.className = "row mb-3 align-items-center";
            fromUpdateContainer.id = "from_update_container";
            fromUpdateContainer.innerHTML = `
            <label class="col-3 col-form-label text-end">From:</label>
                  <div class="col-9">
    <input type="time" class="form-control input_data input_time_schedule" id="time_from_update_input">
  </div>
            
            `;


            let toUpdateContainer = document.createElement("div");
            toUpdateContainer.className = "row mb-3 align-items-center";
            toUpdateContainer.id = "to_update_container";
            toUpdateContainer.innerHTML = `
            <label class="col-3 col-form-label text-end">To:</label>
            <div class="col-9">
            <input class="form-control input_data input_time_schedule" type="time" id="time_to_update_input" placeholder="To">
            </div>
            `;

            dynamicUpdateContainer.appendChild(fromUpdateContainer);
            dynamicUpdateContainer.appendChild(toUpdateContainer);
            dynamicUpdateContainer.appendChild(scheduleTimeUpdateContainer);
            dynamicUpdateContainer.appendChild(dayUpdateContainer);


            break;
        case "3":

            let stateUpdateSelect = document.createElement("select");
            stateUpdateSelect.className = "form-select mx-auto mt-3 mb-0 input_data input_state_threshold";
            stateUpdateSelect.id = "state_threshold_update_select";
            stateUpdateSelect.innerHTML = `
            <option class="state_update_options" selected>Choose state</option>
            <option value="on">On</option>
            <option value="off">Off</option>
            `;

            let durationUpdateInput = document.createElement("input");
            durationUpdateInput.type = "number";
            durationUpdateInput.className = "form-control mx-auto mt-3 mb-0 input_data input_duration_threshold";
            durationUpdateInput.id = "duration_threshold_update_input";
            durationUpdateInput.placeholder = "Duration (minutes)";

            dynamicUpdateContainer.appendChild(durationUpdateInput);
            dynamicUpdateContainer.appendChild(stateUpdateSelect);

            break;
        default:
            console.log("No rule type selected");
    }

}

export {
    generateID,
    changeRuleType,
    createRuleCard,
    createUpdateRuleCard,
    displayUpdateRuleCard,
    updateRuleData,
    changeRuleTypeUpdate
}
