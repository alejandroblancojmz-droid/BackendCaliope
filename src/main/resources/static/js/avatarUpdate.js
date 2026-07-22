// Actualizar avatar en la barra superior
import { api } from "./api.js";

async function updateTopbarAvatar() {
    const avatarImg = document.getElementById("topbarAvatar");
    if (!avatarImg) return;

    try {
        const res = await api("/api/users/me");
        const data = await res.json();
        if (data.picture_avatar) {
            avatarImg.src = `/api/files/${data.picture_avatar}`;
        }else {
            avatarImg.src = "../assets/users-photos/foto-perfil-usuario.png";
        }

    } catch (e) {
        console.log(e)
    }
}

// Ejecutar al cargar la página
document.addEventListener("DOMContentLoaded", () => {
    updateTopbarAvatar();
});