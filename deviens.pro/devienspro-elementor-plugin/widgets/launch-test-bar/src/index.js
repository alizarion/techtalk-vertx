
import React from 'react';
import ReactDOM from 'react-dom';
import LaunchTest from './launch-test';

import reactToWebComponent from 'react-to-webcomponent';
export const webcomponent = reactToWebComponent(LaunchTest, React, ReactDOM);
customElements.define('launch-test-bar', webcomponent);
