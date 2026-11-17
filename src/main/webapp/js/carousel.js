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