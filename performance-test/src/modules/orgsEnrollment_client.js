import { post, get, del } from "./common.js";

function healthCheckInfo(url) {
    return get(url + `/info`, {
        params: { frameworkType: "quarkus" },
        headers: {
            "Ocp-Apim-Subscription-Key": __ENV.API_SUBSCRIPTION_KEY
        }
    })
}

function getOrganizations(url) {
    return get(url + `/organizations`, {
        params: { frameworkType: "quarkus" },
        headers: {
            "Ocp-Apim-Subscription-Key": __ENV.API_SUBSCRIPTION_KEY
        }
    })
}

function getOrganization(url, idOrg) {
    return get(url + `/organizations/${idOrg}`, {
        params: { frameworkType: "quarkus" },
        headers: {
            "Ocp-Apim-Subscription-Key": __ENV.API_SUBSCRIPTION_KEY
        }
    })
}

function createOrganization(url, idOrg) {
    return post(url + `/organizations/${idOrg}`, {
        params: { frameworkType: "quarkus" },
        headers: {
            'Ocp-Apim-Subscription-Key': __ENV.API_SUBSCRIPTION_KEY
        },
    })
}

function removeOrganization(url, idOrg) {
    return del(url + `/organizations/${idOrg}`, {
        params: { frameworkType: "quarkus" },
        headers: {
            "Ocp-Apim-Subscription-Key": __ENV.API_SUBSCRIPTION_KEY
        }
    })
}


module.exports = {
	healthCheckInfo,
	getOrganizations,
    getOrganization,
    createOrganization,
    removeOrganization
}