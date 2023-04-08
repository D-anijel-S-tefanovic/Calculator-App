const button = document.getElementById("button");
const leftOperand = document.getElementById("left-operand");
const rightOperand = document.getElementById("right-operand");
const operators = document.getElementsByName("operator");
const expression = document.getElementById("expression");
const result = document.getElementById("result");

leftOperand.addEventListener("change", (event) => {

  expression.innerHTML = "";
  result.innerHTML = "";

})

rightOperand.addEventListener("change", (event) => {

  expression.innerHTML = "";
  result.innerHTML = "";

})

button.addEventListener("click", (event) => {

  if (leftOperand.value === "" && rightOperand.value === "") {
    alert("Enter both operands");
  } else if (rightOperand.value === "") {
    alert("Enter right operand");
  } else if (leftOperand.value === "") {
    alert("Enter left operand");
  } else {
    for (let operator of operators) {
      if (operator.checked) {
        $.ajax({
          url: `http://localhost:8080/leftOperand=${leftOperand.value}&rightOperand=${rightOperand.value}&operation=${operator.value}`, 
          success: function (output) {
              let expressionInfo = JSON.parse(output).expression;
              let resultInfo = JSON.parse(output).result;           
              expression.innerHTML = expressionInfo;
              result.innerHTML = resultInfo;
          },
          error: function () {
              alert("Processing error, try again.");
          }
      });
      }
    }
    leftOperand.value = "";
    rightOperand.value = "";
  }

});