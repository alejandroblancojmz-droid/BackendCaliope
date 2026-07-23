import { api } from "./api.js";

export const LikeManager = (() => {

  // -------------------------
  // ACTUALIZAR UI
  // -------------------------
  function updateUI(btn, data) {
    const icon = btn.querySelector("i");
    const counter = btn.querySelector(".like-count");

    if (!icon || !counter) return;

    btn.classList.toggle("active", data.liked);

    icon.classList.toggle("bi-heart", !data.liked);
    icon.classList.toggle("bi-heart-fill", data.liked);

    counter.textContent = data.totalLikes;
  }

  // -------------------------
  // CARGAR ESTADO INICIAL DESDE EL BACKEND
  // -------------------------
  async function loadInitialState(btn) {
    const id = btn.getAttribute("data-id");
    if (!id) return;

    try {
      const res = await api(`/api/stories/likes/story/${id}`);
      if (!res || !res.ok) return; // si no hay token válido, api() ya redirige
      const data = await res.json();
      updateUI(btn, data);
    } catch (e) {
      console.error("Error cargando estado del like:", e);
    }
  }

  // -------------------------
  // TOGGLE (dar/quitar like en el backend)
  // -------------------------
  async function handleClick(e) {
    const btn = e.currentTarget;
    const id = btn.getAttribute("data-id");
    if (!id) return;

    btn.disabled = true; // evita doble click mientras responde el servidor

    try {
      const res = await api(`/api/stories/likes/toggle`, {
        method: "POST",
        body: JSON.stringify({ idStories: Number(id) })
      });

      if (!res || !res.ok) throw new Error("Error al procesar el like");

      const data = await res.json();
      updateUI(btn, data);
    } catch (error) {
      console.error(error);
      alert("No se pudo procesar el like");
    } finally {
      btn.disabled = false;
    }
  }

  // -------------------------
  // INIT
  // -------------------------
  async function init(selector) {
    const buttons = document.querySelectorAll(selector);

    for (const btn of buttons) {
      await loadInitialState(btn);
      btn.removeEventListener("click", handleClick);
      btn.addEventListener("click", handleClick);
    }
  }

  return { init };
})();