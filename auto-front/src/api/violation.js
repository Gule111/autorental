import requestHttp from "@/utils/request";

export default {
    async search(start,size,data){
        return await requestHttp.post(`/auto/violation/${start}/${size}`,data);
    },
    async save(data){
        return await requestHttp.post(`/auto/violation`,data);
    },
    async update(data){
        return await requestHttp.put(`/auto/violation`,data);
    },
    async delete(data){
        return await requestHttp.delete(`/auto/violation/${data}`);
    },
};