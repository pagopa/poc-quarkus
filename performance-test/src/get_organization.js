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

var containerFcs = new Array();


export function setup() {
	// 1. setup code (once)
	// The setup code runs, setting up the test environment (optional) and generating data
	// used to reuse code for the same VU
	const params = {
   		headers: {
   			'Content-Type': 'application/json'
   		},
   	};

	for (let i = 0; i < numberOfEventsToPreload; i++) {
		let fc = makeFcMix(25);
		const response = createOrganization(orgsEnrollmentHost, fc, params);
		check(response, { "status is 201": (res) => (res.status === 201) });
		containerFcs.push(fc);
	}

	
	 // return the array with preloaded id
	 return { fcs: containerFcs }
	 
	 // precondition is moved to default fn because in this stage
	 // __VU is always 0 and cannot be used to create env properly
}

function precondition() {
	// no pre conditions
}

// teardown the test data
export function teardown(data) {
	const params = {
   		headers: {
   			'Content-Type': 'application/json'
   		},
   	};

	for (const element of data.fcs) {
		const response = deleteOrganization(orgsEnrollmentHost, element, params);
		check(response, { "status is 200": (res) => (res.status === 200) });
	}
}

export default function(data) {


	// Get an organization
	let tag = {
		orgsEnrollmentMethod: "GetOrganization",
	};

	const params = {
		headers: {
			'Content-Type': 'application/json'
		},
	};
	
	var itemToRecover = getRandomItemFromArray(data.fcs);
	let organizationFiscalCode = itemToRecover
	console.log(orgsEnrollmentHost + 'organization/' + organizationFiscalCode);

	const response = getOrganization(orgsEnrollmentHost, organizationFiscalCode, params);

	console.log(`GetOrganization ... ${response.status}`);

	check(response, {"GetOrganization status is 200": (res) => (res.status === 200)}, tag);
}
