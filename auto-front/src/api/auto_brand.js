import requestHttp from "@/utils/request"
export default{
    async search(start,size,data){
        return await requestHttp.post(`/auto/autoBrand/${start}/${size}`,data)
    },
    async delete(id){ 
        return await requestHttp.delete(`/auto/autoBrand/${id}`)
    },
    async save(data){
        return await requestHttp.post(`/auto/autoBrand`,data)
    },
    async update(data){ 
        return await requestHttp.put(`/auto/autoBrand`,data)
    },
     async selectByMakerId(makerId){
        return await requestHttp.get(`/auto/autoBrand/${makerId}`);
    }
   
}