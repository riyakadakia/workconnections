function GetSelectedText(){
	var e = document.getElementById("state");
	var stateResult = e.options[e.selectedIndex].value;
	console.log(stateResult);
	return stateResult; 
};

function GetSelectedText1(){
	var e = document.getElementById("reasonUnemployed");
	var reasonUmemployedResult = e.options[e.selectedIndex].value;
	console.log(reasonUmemployedResult);
	return reasonUmemployedResult; 
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
	var ele = document.getElementsByName("kids"); 
  	//for(i = 0; i < ele.length; i++) { 
    	if(ele[0].checked){
    		console.log("kidsResult: yes");
    		hideShow('kidHousingForm');
    		hideShow('kidsForm');
    	}
    	else if(ele[1].checked){
    		console.log("kidsResult: no");
    		hideShow('kidsForm');
    	} 
	//} //kidHousingForm
};
function displayRadioValue2() { 
	var ele = document.getElementsByName("multipleKids"); 
  	//for(i = 0; i < ele.length; i++) { 
    	if(ele[0].checked){
    		console.log("multipleKids: yes");
    		hideShow('kidAgeForm');
    		hideShow('multipleKidsForm');
    	}
    	else if(ele[1].checked){
    		console.log("multipleKids: no");
    		hideShow('multipleKidsForm');
    		hideShow('skidAgeForm');
    	} 
	//} 
};
function displayRadioValue3() { 
	var ele = document.getElementsByName("kidHousingForm"); 
  	//for(i = 0; i < ele.length; i++) { 
    	if(ele[0].checked){
    		console.log("kidHousingForm: yes");
    		hideShow('multipleKidsForm');
    		hideShow('kidHousingForm');
    	}
    	else if(ele[1].checked){
    		console.log("multipleKids: no");
    		hideShow('kidHousingForm');
    	} 
	//} 
};

function ageSaveInput()
{
	var x = document.getElementById("ageResult").value;
	console.log(x);
};
function kidAgeSaveInput()
{
	var x = document.getElementById("kidAgeResult").value;
	console.log(x);
};
function ykidAgeSaveInput()
{
	var x = document.getElementById("ykidAgeResult").value;
	console.log(x);
};
function skidAgeSaveInput()
{
	var x = document.getElementById("skidAgeResult").value;
	console.log(x);
};

function GetCheckboxValues() {
    //Create an Array.
    var CSselected = new Array();

    //Reference the Table.
    var tblFruits = document.getElementById("tblCurrentServices");

    //Reference all the CheckBoxes in Table.
    var chks = tblFruits.getElementsByTagName("INPUT");

    // Loop and push the checked CheckBox value in Array.
    for (var i = 0; i < chks.length; i++) {
        if (chks[i].checked) {
            CSselected.push(chks[i].value);
        }
    }

    //Display the selected CheckBox values.
    if (CSselected.length > 0) {
    	console.log(CSselected.join(","));
        hideShow('currentServicesForm');
    }

};