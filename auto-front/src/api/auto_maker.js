import requestHttp from "@/utils/request";
export default{
    async search(start,size,data){
        return await requestHttp.post(`/auto/autoMaker/${start}/${size}`,data)
    },
    async delete(id){
        return await requestHttp.delete(`/auto/autoMaker/${id}`)
    },
    async save(data){
        return await requestHttp.post(`/auto/autoMaker`,data)

    },
     async selectAll(){ 
        return await requestHttp.get(`/auto/autoMaker`)
    },


}