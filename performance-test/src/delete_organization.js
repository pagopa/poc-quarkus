import http from 'k6/http';
import { check } from 'k6';
import { SharedArray } from 'k6/data';

import { getOrganization, createOrganization, deleteOrganization } from "./modules/orgsEnrollment_client.js";
import { makeFcMix, getRandomItemFromArray } from './modules/helpers.js';

export let options = JSON.parse(open(__ENV.TEST_TYPE));

// read configuration
// note: SharedArray can currently only be constructed inside init code
// according to https://k6.io/docs/javascript-api/k6-data/sharedarray
const varsArray = new SharedArray('vars', function() {
	return JSON.parse(open(`./${__ENV.VARS}`)).environment;
});
// workaround to use shared array (only array should be used)
const vars = varsArray[0];
const orgsEnrollmentHost = `${vars.orgsEnrollmentHost}`;
const numberOfEventsToPreload = `${vars.numberOfEventsToPreload}`;

const accountPrimaryKey = `${__ENV.API_SUBSCRIPTION_KEY}`;

var containerFcs = new Array();


export function setup() {
}

function precondition(organizationFiscalCode) {
	// Create the newly created organization.
   	let tag = {
   		gpdMethod: "CreateOrganization",
   	};
   	    const params = {
        headers: {
    		'Content-Type': 'application/json'
        },
    };

    const r = createOrganization(orgsEnrollmentHost, organizationFiscalCode, params);

   	console.log("Create organization " + organizationFiscalCode + " " + r.status);

    check(r, {
    	"CreateOrganization status is 201": (_r) => r.status === 201,
   	}, tag);
}

export default function(data) {

	// Delete an organization
	let tag = {
		orgsEnrollmentMethod: "DeleteOrganization",
	};

	const params = {
		headers: {
			'Content-Type': 'application/json'
		},
	};

	let organizationFiscalCode = "mockOrganizationFiscalCode";

	precondition(organizationFiscalCode, params);

	console.log(orgsEnrollmentHost + 'organization/' + organizationFiscalCode);

	const response = deleteOrganization(orgsEnrollmentHost, organizationFiscalCode, params);

	console.log(`DeleteOrganization ... ${response.status}`);

	check(response, {"DeleteOrganization status is 200": (res) => (res.status === 200)}, tag);
}
