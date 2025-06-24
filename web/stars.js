const starContainer = document.querySelector(".star-container");

function createStar() {
    const star = document.createElementNS("http://www.w3.org/2000/svg", "svg");
    star.setAttribute("viewBox", "0 0 24 24");
    star.setAttribute("width", "12");
    star.setAttribute("height", "12");
    star.style.position = "absolute";
    star.style.top = "-20px";
    star.style.left = `${Math.random() * window.innerWidth}px`;
    star.style.fill = "#ffffff";
    star.style.opacity = Math.random();
    star.innerHTML = `<path d="M12 17.27L18.18 21 16.54 13.97 
    22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24
    7.46 13.97 5.82 21z"/>`;

    document.body.appendChild(star);

    const duration = Math.random() * 8 + 4;
    star.animate([
        { transform: "translateY(0px)" },
        { transform: `translateY(${window.innerHeight}px)` }
    ], {
        duration: duration * 1000,
        iterations: 1,
        easing: "linear"
    });

    setTimeout(() => {
        star.remove();
    }, duration * 1000);
}

setInterval(createStar, 200);
