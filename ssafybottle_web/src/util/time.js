export function getTime() {
    // 2022-10-22 20:22:58
    let today = new Date();
    let time = today.getFullYear() + "-" + (
        today.getMonth() + 1
    ) + "-" + today.getDate() + " " + today.getHours() + ":" + today.getMinutes() +
        ":" + today.getSeconds();
    return time;
}