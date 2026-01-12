import requestHttp from "@/utils/request";

export default {
    async search(start,size,data){
        return await requestHttp.post(`/auto/customer/${start}/${size}`,data);
    },
    async save(data){
        return await requestHttp.post(`/auto/customer`,data);
    },
    async update(data){
        return await requestHttp.put(`/auto/customer`,data);
    },
    async delete(id){
        return await requestHttp.delete(`/auto/customer/${id}`);
    },
    async selectCustomerByTel(data){
        return await requestHttp.post(`/auto/customer/selectCustomerByTel`,data);
    }
};