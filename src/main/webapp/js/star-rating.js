document.addEventListener("DOMContentLoaded", function() {
    const starContainer = document.getElementById("starContainer");
    if (!starContainer) return; // Si no está el componente, no hace nada

    const stars = starContainer.querySelectorAll(".star");
    const punctuationInput = document.getElementById("punctuationInput");
    const scoreText = document.getElementById("scoreText");
    const btnPlus = document.getElementById("btnPlus");
    const btnMinus = document.getElementById("btnMinus");
    
    let currentValue = parseFloat(punctuationInput.value) || 5.0;

    function updateDisplay(val) {
        currentValue = Math.max(1.0, Math.min(5.0, val));
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
            if (rating < 1.0) rating = 1.0;
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