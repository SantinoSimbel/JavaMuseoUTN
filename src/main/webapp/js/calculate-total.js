
document.addEventListener("DOMContentLoaded", function () {

    // apuntamos a los elementos
    const quantityInput = document.getElementById("quantity");
    const totalPrice = document.getElementById("totalPrice");
    const totalTickets = document.getElementById("totalTickets");

    // recuperamos los datos del jsp del div invisible
    const purchaseData = document.getElementById("purchaseData");

    if (!purchaseData || !quantityInput || !totalPrice) {
        return;
    }

    const price = Number(purchaseData.dataset.price);
    const previousQuantity = Number(purchaseData.dataset.previousQuantity);
    const previousTotal = Number(purchaseData.dataset.previousTotal);

    function updateTotal() {

        let quantity = Number(quantityInput.value);

        // para no mostrar cálculos extraños
        if (!Number.isFinite(quantity) || quantity < 1 || !Number.isInteger(quantity)) {
            quantity = 1;
        }

        // entradas anteriores + entradas nuevas
        const finalQuantity = previousQuantity + quantity;

        // importe anterior + importe de las entradas nuevas
        const finalPrice = previousTotal + (price * quantity);

        if (totalTickets) {
            totalTickets.textContent = finalQuantity;
        }

        totalPrice.textContent = Math.round(finalPrice);
    }

    quantityInput.addEventListener("input", updateTotal);
    updateTotal();
});
