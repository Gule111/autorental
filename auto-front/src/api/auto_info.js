import requestHttp from "@/utils/request";

export default{
    async search(start,size,data){
        return await requestHttp.post(`/auto/autoInfo/${start}/${size}`,data)
    },
    async save(data){
        return await requestHttp.post(`/auto/autoInfo`,data)
    },
    async update(data){
        return await requestHttp.put(`/auto/autoInfo`,data)
    },
    async delete(data){
        return await requestHttp.delete(`/auto/autoInfo/${data}`)
    },
    async exist(data){
        return await requestHttp.post(`/auto/autoInfo/exist`,data)
    },
    //获取所有待保养车辆
    async toBeMaintain(){
        return await requestHttp.get(`/auto/autoInfo/toBeMaintain`)
    }
}