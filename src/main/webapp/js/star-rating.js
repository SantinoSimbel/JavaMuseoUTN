document.addEventListener("DOMContentLoaded", function() {
    const starContainer = document.getElementById("starContainer");
    if (!starContainer) return;

    const stars = starContainer.querySelectorAll(".star");
    const punctuationInput = document.getElementById("punctuationInput");
    const scoreText = document.getElementById("scoreText");
    const btnPlus = document.getElementById("btnPlus");
    const btnMinus = document.getElementById("btnMinus");
    
    // Si el valor guardado es 0, parseFloat("0") da 0 (falsy), así que validamos contra NaN
    let parsedVal = parseFloat(punctuationInput.value);
    let currentValue = isNaN(parsedVal) ? 5.0 : parsedVal;

    function updateDisplay(val) {
        // 1. Bajamos el límite mínimo de 1.0 a 0.0
        currentValue = Math.max(0.0, Math.min(5.0, val));
        punctuationInput.value = currentValue;
        scoreText.textContent = currentValue.toFixed(1) + "★";

        stars.forEach((star, index) => {
            let starNum = index + 1;
            star.className = "bi star ";
            if (currentValue >= starNum) {
                star.classList.add("bi-star-fill", "text-warning");
            } else if (currentValue >= starNum - 0.5) {
                star.classList.add("bi-star-half", "text-warning");
            } else {
                star.classList.add("bi-star", "text-muted");
            }
        });
    }

    stars.forEach((star, index) => {
        star.addEventListener("click", function(e) {
            const rect = star.getBoundingClientRect();
            const clickX = e.clientX - rect.left;
            const isLeftHalf = clickX < (rect.width / 2);
            
            let rating = index + (isLeftHalf ? 0.5 : 1.0);
            
            if (rating === currentValue) {
                rating = 0.0;
            }
            
            updateDisplay(rating);
        });
    });

    if (btnPlus) {
        btnPlus.addEventListener("click", () => updateDisplay(currentValue + 0.5));
    }
    if (btnMinus) {
        btnMinus.addEventListener("click", () => updateDisplay(currentValue - 0.5));
    }

    updateDisplay(currentValue);
});