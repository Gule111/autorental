import requestHttp from "@/utils/request";


export default {
    async save(data){
        return await requestHttp.post("/auto/order",data)
    },
    async searchUnfinished(start,size,data){
        return await requestHttp.post(`/auto/order/unfinished/${start}/${size}`,data)
    },
    async update(data){
        return await requestHttp.put(`/auto/order`,data);
    },
    async search(start,size,data){
        return await requestHttp.post(`/auto/order/${start}/${size}`,data)
    },
    async countViolation(auto_id){
        return await requestHttp.get(`/auto/order/countViolation/${auto_id}`)
    },
    async doReturnDeposit(orderId){
        return await requestHttp.get(`/auto/order/doReturnDeposit/${orderId}`)
    }
}