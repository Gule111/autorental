import requestHttp from "@/utils/request";

export default {
    async search() { 
        return await requestHttp.get("/auto/permission");
    },
    async tree() { 
        return await requestHttp.get("/auto/permission/tree");
    },
    async save(data) { 
        return await requestHttp.post("/auto/permission", data);
    },
    async update(data) {
        return await requestHttp.put("/auto/permission", data);
     },
    async delete(id) {
        return await requestHttp.delete(`/auto/permission/${id}`);
     },
    async hasChildren(id) { 
        return await requestHttp.get(`/auto/permission/hasChildren/${id}`);
    },
};