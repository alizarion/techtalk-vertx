// app-client.js
import React, {Component} from 'react'
import {render} from 'react-dom'
import EventBus from 'vertx3-eventbus-client'
import S from 'shorti'
import _ from 'lodash'
import {Input} from 'react-bootstrap'
import PersonService from '../service-api/src/main/generated/person-service-js/person_service-proxy'

class App extends Component {

    constructor() {
        super();
        this.state = {
            data: {
                persons: []
            }
        };
        this.eventBus = null;
        this.personService = null;
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.componentDidMount = this.componentDidMount.bind(this);

    }

    componentDidMount() {
        this.eventBus = new EventBus('/eventbus');
        let self = this;
        this.eventBus.onopen = function () {
            console.log("EventBus is open")
            self.personService = new PersonService(self.eventBus, "persons");
            self.personService.personList((err, result) => {
                if (!err) {
                    self.setState({
                            data: {
                                persons: result.body
                            }
                        }
                    )
                } else {
                    console.warn(err)
                }
            })
        }
    }

    handleSubmit(e) {
        const eb = this.eventBus;
        e.preventDefault();
        console.log(this.personService);
        this.personService
            .createPerson(
                {firstName: this.state.firstName,lastName:this.state.lastName},(err,result)=> {
            if (!err) {
                this.personService.personList((err, result) => {
                    if (!err) {
                        this.setState({
                            data :
                                {
                                    persons : result.body
                                }
                        })
                    } else {
                        console.warn(err)
                    }
                })
            } else {
                console.warn(err)
            }
        });
        console.log (this.state);
    }

    handleInputChange(event) {
        const target = event.target;
        const name = target.name;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        if(value && name){
            this.setState({
                [name]: value
            });
        }

    }


    render() {

        return (
            <div>
                <div style={S('pl-15')}>
                    <h2>React Chat App</h2>
                    <div ref="messages_scroll_area">
                        <ul style={S('p-0')}>gtsds</ul>
                    </div>
                </div>
                <div style={S('absolute b-0 w-100p pl-15 pr-15')}>
                    <form  onSubmit={ this.handleSubmit }>
                        <div>
                            <label htmlFor="firstName">firstName</label><br />
                            <Input id="firstName" type="text" onChange={this.handleInputChange}  name="firstName" ref="firstName" />
                        </div>
                        <div>
                            <label htmlFor="lastName">lastName</label><br />
                            <Input id="lastName"  type="text"  onChange={this.handleInputChange}  name="lastName" ref="lastName" />
                        </div>
                        <button type="submit" >create Person</button>
                    </form>
                </div>
            </div>
        )
    }
}

const app = document.getElementById('app');
render(<App/>, app);