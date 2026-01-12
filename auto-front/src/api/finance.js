import requestHttp from "@/utils/request";

export default {
    async countDayRental() {
        return requestHttp.get("/auto/finance/countDayRental");
    },
    async countDayReturn() {
        return requestHttp.get("/auto/finance/countDayReturn");
    },
    async countDayCost(){
        return requestHttp.get("/auto/finance/countDayCost");
    },
    async countMonthRental() {
        return requestHttp.get("/auto/finance/countMonthRental");
    },
    async countMonthReturn() {
        return requestHttp.get("/auto/finance/countMonthReturn");
    },
    async countMonthCost(){
        return requestHttp.get("/auto/finance/countMonthCost");
    }
};