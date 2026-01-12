import requestHttp from "@/utils/request";

export default{
    async search(start,size,data){
        return await requestHttp.post(`/auto/rentalType/${start}/${size}`,data);
    },
    async save(data){
        return await requestHttp.post(`/auto/rentalType`,data);
    },
    async update(data){
        return await requestHttp.put(`/auto/rentalType`,data);
    },
    async delete(data){
        return await requestHttp.delete(`/auto/rentalType/${data}`);
    },
    async selectAll(){
        return await requestHttp.get(`/auto/rentalType`);
    },
    
}