function moveCarousel(carouselId, direction) {
    const carousel = document.getElementById(carouselId);
    if (!carousel) return;

    const firstCard = carousel.querySelector('.carousel-card');
    if (!firstCard) return;

    const cardWidth = firstCard.getBoundingClientRect().width;
    const gap = parseFloat(getComputedStyle(carousel).columnGap) || 0;

    carousel.scrollBy({
        left: direction * (cardWidth + gap),
        behavior: 'smooth'
    });
}

// Muestra u oculta las flechas de un carrusel según su posición de scroll
function updateArrows(carousel) {
    // Estructura: wrapper > div.overflow-hidden > carousel-track, y las flechas son hijas del wrapper
    const wrapper = carousel.parentElement.parentElement;
    const left = wrapper.querySelector('.carousel-arrow-left');
    const right = wrapper.querySelector('.carousel-arrow-right');

    const maxScroll = carousel.scrollWidth - carousel.clientWidth;
    const noOverflow = maxScroll <= 1;                  // todas las tarjetas entran en pantalla
    const atStart = carousel.scrollLeft <= 1;
    const atEnd = carousel.scrollLeft >= maxScroll - 1;

    if (left) left.classList.toggle('d-none', noOverflow || atStart);
    if (right) right.classList.toggle('d-none', noOverflow || atEnd);
}

function initCarousels() {
    document.querySelectorAll('.carousel-track').forEach(function (carousel) {
        carousel.addEventListener('scroll', function () { updateArrows(carousel); });
        updateArrows(carousel);
    });
}

document.addEventListener('DOMContentLoaded', initCarousels);

// Al cambiar el tamaño de la ventana cambian las tarjetas visibles (1, 2 o 3)
window.addEventListener('resize', function () {
    document.querySelectorAll('.carousel-track').forEach(updateArrows);
});

// Por si las imágenes cambian el ancho una vez cargadas
window.addEventListener('load', function () {
    document.querySelectorAll('.carousel-track').forEach(updateArrows);
});