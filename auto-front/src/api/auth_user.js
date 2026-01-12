import requestHttp from "@/utils/request";

export default{
    async search(start,size,data){
        return await requestHttp.post(`/auto/user/${start}/${size}`,data);
    },
    async save(data){
        return await requestHttp.post(`/auto/user/save`,data);
    },
    async update(data){
        return await requestHttp.put(`/auto/user`,data);
    },
    async delete(id){
        return await requestHttp.delete(`/auto/user/${id}`);
    },
    async selectRoleIdByUserId(id){
        return await requestHttp.get(`/auto/user/role/${id}`);
    },
    async bindRole(userId,roleIds){
        return await requestHttp.get(`/auto/user/bind/${userId}/${roleIds}`);
    }
}