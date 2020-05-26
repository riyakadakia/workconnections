function GetSelectedText(){
	var e = document.getElementById("state");
	var stateResult = e.options[e.selectedIndex].value;
	console.log(stateResult);
	return stateResult; 
};

function GetSelectedText1(){
	var e = document.getElementById("reasonUnemployed");
	var reasonUnemployedResult = e.options[e.selectedIndex].value;
	console.log(reasonUnemployedResult);
    hideShow('reasonUnemployedForm');
    hideShow('limitedForm');
	return reasonUnemployedResult; 
}
function GetSelectedText2(){
    var e = document.getElementById("kidRelation");
    var kidRelationResult = e.options[e.selectedIndex].value;
    console.log(kidRelationResult);
    if (kidRelationResult == "5") {
        hideShow('kidRelationForm');
        hideShow('liveAloneForm');
    } 
    else
    {
        hideShow('kidRelationForm'); 
        hideShow('multipleKidsForm');
    }

    return kidRelationResult; 
}
function GetSelectedText3(){
    var e = document.getElementById("citizen");
    var citizenResult = e.options[e.selectedIndex].value;
    console.log(citizenResult); 
    document.getElementById("citizenResult").value = citizenResult;
    return citizenResult; 
}

function hideShow(id)
{
	var x = document.getElementById(id);
	if (x.style.display == "none") {
	  x.style.display = "block";
	} else {
	  x.style.display = "none";
	}
};

function Dropdown() {
  document.getElementById("myDropdown").classList.toggle("show");
};

window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
};

function displayRadioValue() { 
	var ele = document.getElementsByName("kidsForm"); 
  	var kidsResult = document.querySelector('input[name="kids"]:checked').value
    	if(kidsResult == "0"){
    		console.log("kidsResult: yes");
    		hideShow('kidHousingForm');
    		hideShow('kidsForm');
    	}
    	else if(kidsResult == "1"){
    		console.log("kidsResult: no");
    		hideShow('kidsForm');
            hideShow('liveAloneForm');
    	} 
};
function displayRadioValue2() { 
	var ele = document.getElementsByName("multipleKidsForm"); 
    var multipleKidsResult = document.querySelector('input[name="multipleKids"]:checked').value
    	if(multipleKidsResult == "0"){
    		console.log("multipleKids: yes");
    		hideShow('kidAgeForm');
    		hideShow('multipleKidsForm');
    	}
    	else if(multipleKidsResult == "1"){
    		console.log("multipleKids: no");
    		hideShow('multipleKidsForm');
    		hideShow('skidAgeForm');
    	} 
	//} 
};
function displayRadioValue3() { 
	var ele = document.getElementsByName("kidHousingForm"); 
    var kidHousingResult = document.querySelector('input[name="kidHousing"]:checked').value
    console.log("limited: ", limitedResult);
        if(kidHousingResult == "0"){
            console.log("kidHousingForm: 0");
            hideShow('kidRelationForm');
            hideShow('kidHousingForm');
        }
        else
        {
            hideShow('kidHousingForm');
            hideShow('liveAloneForm');
        }
    return kidHousingResult;
};
function displayRadioValue4()
{ 
    var ele = document.getElementsByName("limitedForm"); 
    var limitedResult = document.querySelector('input[name="limited"]:checked').value
    console.log("limited: ", limitedResult);
        if(limitedResult == "0"){
            console.log("limitedInIfLoop: ", limitedResult);
        }
    hideShow('limitedForm');
    hideShow('citizenForm');
    return limitedResult;
};
function schoolingRadio()
{
    var ele = document.getElementsByName("schoolingForm"); 
    var schoolingResult = document.querySelector('input[name="schooling"]:checked').value
    console.log("schooling: ", schoolingResult);
    hideShow('schoolingForm');
    //hideShow('citizenForm');
}
function medicalRightsRadio()
{
    var ele = document.getElementsByName("medicalRightsForm"); 
    var medicalRightsResult = document.querySelector('input[name="medicalRights"]:checked').value
    console.log("medicalRightsResult: ", medicalRightsResult);

}
function ImmunizationRadio()
{
    var ele = document.getElementsByName("ImmunizationForm"); 
    var ImmunizationResult = document.querySelector('input[name="Immunization"]:checked').value
    console.log("Immunization: ", ImmunizationResult);
}
function drugRadio()
{
    var ele = document.getElementsByName("drugForm"); 
    var drugResult = document.querySelector('input[name="drug"]:checked').value
    console.log("drugResult: ", drugResult);   
}


function ageSaveInput()
{
	var ageResult = document.getElementById("ageResult").value;
	console.log(ageResult);
};
function kidAgeSaveInput()
{
	var olderKidAgeResult = document.getElementById("kidAgeResult").value;
	console.log(olderKidAgeResult);
};
function ykidAgeSaveInput()
{
	var youngerKidAgeResult = document.getElementById("ykidAgeResult").value;
	console.log(youngerKidAgeResult);


};
function skidAgeSaveInput()
{
	var singleKidAgeResult = document.getElementById("skidAgeResult").value;
	console.log(singleKidAgeResult);
};

function GetCheckboxValues() {
    //Create an Array.
    var CSselected = new Array();

    //Reference the Table.
    var tblFruits = document.getElementById("tblCurrentServices");

    //Reference all the CheckBoxes in Table.
    var chks = tblFruits.getElementsByTagName("INPUT");

    // Loop and push the checked CheckBox value in Array.
    for (var i = 0; i < chks.length; i++) 
    {
        if (chks[i].checked) 
        {
            CSselected.push(chks[i].value);
        }
    }
    var ele = document.getElementsByName("currentServicesForm"); 
    var CSResult = document.querySelector('input[name="CS"]:checked').value
    console.log("CSResult: ", CSResult);   

    //Display the selected CheckBox values.
    if (CSselected.length > 0) 
    {
    	console.log(CSselected.join(","));
        hideShow('currentServicesForm');
    }
}
function GetSelectedText4()
{
    var e = document.getElementById("disabled");
    var disabledResult = e.options[e.selectedIndex].value;
    console.log("disabledResult: ", disabledResult);
    hideShow('ageForm');
    hideShow('disabledForm');
    document.getElementById("disabledResult").value = disabledResult;
    return disabledResult;
}
function liveAloneRadio()
{
    var ele = document.getElementsByName("liveAloneForm"); 
    var LAResult = document.querySelector('input[name="LA"]:checked').value
    console.log("LAResult: ", LAResult);
    if (LAResult == "0")
    {
        hideShow('spouseForm')
    }
    else
    {
        hideShow('foodSepForm');
    }

}
function foodSepRadio()
{
    var ele = document.getElementsByName("foodSepForm"); 
    var foodSepResult = document.querySelector('input[name="foodSep"]:checked').value
    console.log("foodSepResult: ", foodSepResult);
    if (foodSepResult == "0")
    {
        hideShow('spouseForm');
    }
    else
    {
        hideShow('WfoodSepForm');
    }

}
function WfoodSepRadio()
{
    var ele = document.getElementsByName("WfoodSepForm"); 
    var WfoodSepResult = document.querySelector('input[name="WfoodSep"]:checked').value
    console.log("WfoodSepResult: ", WfoodSepResult);
    hideShow('spouseForm');

}
function spouseRadio()
{
    var ele = document.getElementsByName("spouseForm"); 
    var spouseResult = document.querySelector('input[name="spouse"]:checked').value
    console.log("spouseResult: ", spouseResult);
    if (spouseResult == "0")
    {
        hideShow('spouseLiveForm');
    }
    else
    {
        hideShow('familyCitizenForm');
    }

}
function familyCitizenRadio()
{
    var ele = document.getElementsByName("familyCitizenForm"); 
    var EnumerationResult = document.querySelector('input[name="familyCitizen"]:checked').value
    console.log("familyCitizenResult: ", familyCitizenResult);
    hideShow('EnumerationForm');
}
function EnumerationRadio()
{
    var ele = document.getElementsByName("EnumerationForm"); 
    var EnumerationResult = document.querySelector('input[name="Enumeration"]:checked').value
    console.log("EnumerationResult: ", EnumerationResult);
    hideShow("WorkRegistrationForm");
}
function spouseLiveRadio()
{
    var ele = document.getElementsByName("spouseLiveForm"); 
    var spouseLiveResult = document.querySelector('input[name="spouseLive"]:checked').value
    console.log("spouseLiveResult: ", spouseLiveResult);
    hideShow('familyCitizenForm');

}
function WorkRegistrationRadio()
{
    var ele = document.getElementsByName("WorkRegistrationForm"); 
    var WorkRegistrationResult = document.querySelector('input[name="WorkRegistration"]:checked').value
    console.log("WorkRegistrationResult: ", WorkRegistrationResult);
    hideShow('petForm');
}
function ableWorkRadio()
{
    var ele = document.getElementsByName("ableWorkForm"); 
    var ableWorkResult = document.querySelector('input[name="ableWork"]:checked').value
    console.log("ableWorkResult: ", ableWorkResult);
    if (ableWorkResult == "0")
    {
        hideShow('activeSeekingForm');
    }
    else
    {
        hideShow('minimunWageForm')
    }
}
function activeSeekingRadio()
{
    var ele = document.getElementsByName("activeSeekingForm"); 
    var activeSeekingResult = document.querySelector('input[name="activeSeeking"]:checked').value
    console.log("activeSeekingResult: ", activeSeekingResult);
    hideShow('minimunWageForm')
}
function minimunWageRadio()
{
    var ele = document.getElementsByName("minimunWageForm"); 
    var minimunWageResult = document.querySelector('input[name="minimunWage"]:checked').value
    console.log("minimunWageResult: ", minimunWageResult);
    hideShow('otherChildForm')
}
function otherChildRelationRadio()
{
    var ele = document.getElementsByName("otherChildRelationForm"); 
    var otherChildRelationResult = document.querySelector('input[name="otherChildRelation"]:checked').value
    console.log("otherChildRelationResult: ", otherChildRelationResult);
    if (otherChildRelationResult == "0")
    {
        hideShow('kidcitizenForm')
    }
    else
    {
        hideShow('SubmitForm')
    }
}
function GetSelectedTextCitizen()
{
    var e = document.getElementById("kidcitizen");
    var kidcitizenResult = e.options[e.selectedIndex].value;
    console.log("kidcitizenResult", kidcitizenResult); 
    document.getElementById("kidcitizenResult").value = kidcitizenResult;
    if (kidcitizenResult != "4")
    {
        hideShow('legalCustodyForm')
    }
    else
    {
        hideShow('SubmitForm')
    }
}
function legalCustodyRadio()
{
    var ele = document.getElementsByName("legalCustodyForm"); 
    var legalCustodyResult = document.querySelector('input[name="legalCustody"]:checked').value
    console.log("legalCustodyResult: ", legalCustodyResult);
    if (legalCustodyResult == "0")
    {
        hideShow('kidEarningForm')
    }
    else
    {
        hideShow('SubmitForm')
    }
}
function kidEarningRadio()
{
    var ele = document.getElementsByName("kidEarningForm"); 
    var kidEarningResult = document.querySelector('input[name="kidEarning"]:checked').value
    console.log("kidEarningResult: ", kidEarningResult);
    if (kidEarningResult == "0")
    {
        hideShow('monthlyEarningsForm')
    }
    else
    {
        hideShow('immunityForm')
    }
}
function immunityRadio()
{
    var ele = document.getElementsByName("immunityForm"); 
    var immunityResult = document.querySelector('input[name="immunity"]:checked').value
    console.log("immunityResult: ", immunityResult);
    if (immunityResult == "0")
    {
        hideShow('annualIncomeForm')
    }
    else
    {
        hideShow('SubmitForm')
    }
}
function monthlyEarningsRadio()
{
    var ele = document.getElementsByName("monthlyEarningsForm"); 
    var monthlyEarningsResult = document.querySelector('input[name="monthlyEarnings"]:checked').value
    console.log("monthlyEarningsResult: ", monthlyEarningsResult);
    if (monthlyEarningsResult == "0")
    {
        hideShow('immunityForm')
    }
    else
    {
        hideShow('SubmitForm')
    }
}
function annualIncomeRadio()
{
    var ele = document.getElementsByName("annualIncomeForm"); 
    var annualIncomeResult = document.querySelector('input[name="annualIncome"]:checked').value
    console.log("annualIncomeResult: ", annualIncomeResult);
    if (annualIncomeResult == "0")
    {
        hideShow('otherStateRightsForm')
    }
    else
    {
        hideShow('SubmitForm')
    }
}
function otherStateRightsRadio()
{
    var ele = document.getElementsByName("otherStateRightsForm"); 
    var otherStateRightsResult = document.querySelector('input[name="otherStateRights"]:checked').value
    console.log("otherStateRightsResult: ", otherStateRightsResult);
    if (otherStateRightsResult == "0")
    {
        hideShow('SSNumForm')
    }
    else
    {
        hideShow('SubmitForm')
    }
}
function SSNumRadio()
{
    var ele = document.getElementsByName("SSNumForm"); 
    var SSNumResult = document.querySelector('input[name="SSNum"]:checked').value
    console.log("SSNumResult: ", SSNumResult);
    if (SSNumResult == "0")
    {
        hideShow('parentsForm')
    }
    else
    {
        hideShow('SubmitForm')
    }
}
function parentsRadio()
{
    var ele = document.getElementsByName("parentsForm"); 
    var parentsResult = document.querySelector('input[name="parents"]:checked').value
    console.log("parentsResult: ", parentsResult);
    hideShow('SubmitForm')
}
function otherChildRadio()
{
    var ele = document.getElementsByName("otherChildForm"); 
    var otherChildResult = document.querySelector('input[name="otherChild"]:checked').value
    console.log("otherChildResult: ", otherChildResult);
    if (otherChildResult == "0")
    {
        hideShow('otherChildRelationForm')
    }
    else
    {
        hideShow('SubmitForm')
    }
}

function petRadio()
{
    var ele = document.getElementsByName("petForm"); 
    var petResult = document.querySelector('input[name="pet"]:checked').value
    console.log("petResult: ", petResult);
    hideShow('ableWorkForm');
}
function KCSP()
{
    parentsResult = document.querySelector('input[name="parents"]:checked').value;
    if (parentsResult == "1")
    {
        hideShow('KCSPForm');
    }
}
function SSI()
{
    console.log("SSI is called")
    disabledResult = document.getElementById("disabledResult").value;
    limitedResult = document.querySelector('input[name="limited"]:checked').value;
    citizenResult = document.getElementById("citizenResult").value;
    ageResult = document.getElementById("ageResult").value;

    if (ageResult>="65"|| disabledResult != "3")
    {
        if(limitedResult == "0" && citizenResult != "4")
        {
            hideShow('SSIForm');
        }
    }


}
function PetFoodStamps()
{
    console.log("PetFoodStamps is called")
    petResult = document.querySelector('input[name="pet"]:checked').value;
    if (petResult == "0")
    {
        console.log("In the PetFoodStamps function")
        hideShow('PetsForm');
    }
}
function SNAP()
{
    console.log("In the SNAP function");
    EnumerationResult = document.querySelector('input[name="Enumeration"]:checked').value;
    familyCitizenResult = document.querySelector('input[name="familyCitizen"]:checked').value;
    WorkRegistrationResult = document.querySelector('input[name="WorkRegistration"]:checked').value;
    if (EnumerationResult == "0" && familyCitizenResult == "0")
    {
        if (WorkRegistrationResult == "0")
        {
            hideShow('SNAPForm');
        }
    }
}
function LWC()
{
    console.log("LWC is called")
    ableWorkResult = document.querySelector('input[name="ableWork"]:checked').value;
    activeSeekingResult = document.querySelector('input[name="activeSeeking"]:checked').value;
    minimunWageResult = document.querySelector('input[name="minimunWage"]:checked').value;
    reasonUnemployedResult = document.getElementById("reasonUnemployed").value;

    if (reasonUnemployedResult == "2" || reasonUnemployedResult == "3" || reasonUnemployedResult == "4" || reasonUnemployedResult == "5")
    {
        if (minimunWageResult == "0" || minimunWageResult == "2")
        {
            if (ableWorkResult == "0")
            {
                if (activeSeekingResult == "0")
                {
                    hideShow('LWCForm');
                }
            }
        }
    }
}
function CARES()
{
    console.log("In the Cares function");
    reasonUnemployedResult = document.getElementById("reasonUnemployed").value;
    if (reasonUnemployedResult == "3" || reasonUnemployedResult == "4")
    {
        hideShow('CARESForm')
    }
}
function FITAP()
{
    singleKidAgeResult = "0"
    youngerKidAgeResult = "0"
    schoolingResult = "1"
    medicalRightsResult = "1"
    ImmunizationResult = "1"
    kidHousingResult = "1"
    drugResult = "1"
    console.log("FITAP is called")
    citizenResult = document.getElementById("citizenResult").value;
    kidRelationResult = document.getElementById("kidRelationResult").value;
    kidHousingResult = document.querySelector('input[name="kidHousing"]:checked').value;
    singleKidAgeResult = document.getElementById("skidAgeResult").value;
    youngerKidAgeResult = document.getElementById("ykidAgeResult").value;
    schoolingResult = document.querySelector('input[name="schooling"]:checked').value;
    medicalRightsResult = document.querySelector('input[name="medicalRights"]:checked').value;
    ImmunizationResult = document.querySelector('input[name="Immunization"]:checked').value;
    drugResult = document.querySelector('input[name="drug"]:checked').value;
    console.log("drugResult: ",drugResult)
 
    if (singleKidAgeResult <= "18" || youngerKidAgeResult <= "18")
    {
        if (schoolingResult == "0" && citizenResult == "0")
        {
            //
            if (kidHousingResult == "0" && kidRelationResult != "5")
            {
                if (medicalRightsResult == "0" && ImmunizationResult == "0")
                {
                    if (drugResult == "0")
                    {
                        hideShow('FITAPForm');
                    }
                }

            }
        }
    }
}
function LaCap()
{
    WfoodSepResult = "1"
    foodSepResult = "1"  
    console.log("LaCap is called")
    citizenResult = document.getElementById("citizenResult").value;
    ageResult = document.getElementById("ageResult").value;
    CSResult = document.querySelector('input[name="CS"]:checked').value;
    LAResult = document.querySelector('input[name="LA"]:checked').value;
    WfoodSepResult = document.querySelector('input[name="WfoodSep"]:checked').value;
    foodSepResult = document.querySelector('input[name="foodSep"]:checked').value;
    kidHousingResult = document.querySelector('input[name="kidHousing"]:checked').value;
    kidsResult = document.querySelector('input[name="kids"]:checked').value;
    multipleKidsResult = document.querySelector('input[name="multipleKids"]:checked').value
    singleKidAgeResult = document.getElementById("skidAgeResult").value;
    youngerKidAgeResult = document.getElementById("ykidAgeResult").value;
    spouseResult = document.querySelector('input[name="spouse"]:checked').value
    spouseLiveResult = document.querySelector('input[name="spouseLive"]:checked').value

    if (ageResult>="60" && citizenResult != "4" && CSResult == "4")
    {   
        if (LAResult == "0" || foodSepResult == "0" || WfoodSepResult == "0")
        {
            if (kidsResult == "0")
            {
                if (kidHousingResult == "0")
                {
                    if (multipleKidsResult == "1")
                    {
                        if (singleKidAgeResult < "22")
                        {
                            if (spouseResult == "0")
                            {
                                if (spouseLiveResult == "1")
                                {
                                     console.log("inside the ifs of LaCap")
                                    hideShow('LaCapForm');
                                }
                            }
                            else
                            {
                                console.log("inside the ifs of LaCap")
                                hideShow('LaCapForm');
                            }
                        }

                    }
                    else
                    {
                        if (youngerKidAgeResult < "22")
                        {
                            if (spouseResult == "0")
                            {
                                if (spouseLiveResult == "1")
                                {
                                     console.log("inside the ifs of LaCap")
                                     hideShow('LaCapForm');
                                }
                            }
                            else
                            {
                                 console.log("inside the ifs of LaCap")
                                 hideShow('LaCapForm');
                            }
                        }
                    }
                }
                else
                {
                    if (spouseResult == "0")
                    {
                        if (spouseLiveResult == "1")
                        {
                             console.log("inside the ifs of LaCap")
                             hideShow('LaCapForm');
                        }
                    }
                    else
                    {
                         console.log("inside the ifs of LaCap")
                         hideShow('LaCapForm');
                    }
                }

            }
            else
            {
                if (spouseResult == "0")
                {
                    if (spouseLiveResult == "1")
                    {
                         console.log("inside the ifs of LaCap")
                        hideShow('LaCapForm');
                    }
                }
                else
                {
                     console.log("inside the ifs of LaCap")
                     hideShow('LaCapForm');
                }
            }
               

        }
    }

    
}