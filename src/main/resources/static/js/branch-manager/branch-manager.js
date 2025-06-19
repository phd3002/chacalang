document.addEventListener("DOMContentLoaded", () => {
    fetch(`/branch-manager/dashboard/${BRANCH_ID}`)
        .then(response => {
            if (!response.ok) throw new Error("Lỗi khi tải dashboard");
            return response.json();
        })
        .then(data => {
            document.getElementById("totalOrders").textContent = data.totalOrders;
            document.getElementById("totalRevenue").textContent = formatCurrency(data.totalRevenue);
            document.getElementById("todayReservations").textContent = data.todayReservations;

            document.getElementById("pendingOrders").textContent = data.processingOrders;
            document.getElementById("completedOrders").textContent = data.completedOrders;
            document.getElementById("cancelledOrders").textContent = data.cancelledOrders;
        })
        .catch(error => {
            console.error("Lỗi khi gọi API Dashboard:", error);
        });
});

function formatCurrency(value) {
    if (!value) return "0₫";
    return Number(value).toLocaleString("vi-VN", { style: "currency", currency: "VND" });
}
