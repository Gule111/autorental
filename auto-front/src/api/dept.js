import requestHttp from "@/utils/request";

export default{
    async search(params){
        return await requestHttp.post("/auto/dept",params)
    },
    async selectTree(){
        return await requestHttp.get("/auto/dept")
    },
    async save(params){
        return await requestHttp.post("/auto/dept/save",params)
    },
    async update(params){
        return await requestHttp.put("/auto/dept",params)
    },
    async delete(id){
        return await requestHttp.delete(`/auto/dept/${id}`)
    },
    async hasChildren(id){
        return await requestHttp.get(`/auto/dept/${id}`)
    }
}