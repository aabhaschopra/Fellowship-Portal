const menuIcon = document.querySelector(".hamburger-menu");
const side_drawer = document.querySelector(".side-drawer");

// =============== NAV BAR ===================
menuIcon.addEventListener("click", function () {
	// game_section.scrollIntoView();
	side_drawer.classList.toggle("change");
});
