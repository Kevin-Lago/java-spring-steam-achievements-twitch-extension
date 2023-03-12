import React, { Component } from 'react';
import ajax from 'ajax';
import './App.css';
import Achievement from './components/achievement/Achievement';
import Header from './components/header/Header';
import Game from './components/game/Game';

export default class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            twitchBackgroundColor: "#0e0e10",
            backgroundColor: "#202020",
            headerHeight: "3em",
            themeColor: "#00FFFF",
            contentColor: "#FFFFFF",
            margin: ".2em",
            game: null,
            games: [
                {
                    name: "Garrys Mod",
                    image: "Test Image",
                    achievements: []
                },
                {
                    name: "Ori and the Blind Forest",
                    image: "Test Image",
                    achievements: []
                },
                {
                    name: "World of Tanks",
                    image: "Test Image",
                    achievements: []
                },
                {
                    name: "Garrys Mod",
                    image: "Test Image",
                    achievements: []
                },
                {
                    name: "Ori and the Blind Forest",
                    image: "Test Image",
                    achievements: []
                },
                {
                    name: "World of Tanks",
                    image: "Test Image",
                    achievements: []
                },
            ]
        };
    }

    componentDidMount() {
        ajax.get("localhost:8080/api/games/test")
            .then(data => {
                console.log(data)
            })
            .error(error => {

            });
    }

    fetchAchievements() {

    }

    render() {
        return (
            <div id="app" style={{
                backgroundColor: `${this.state.backgroundColor}`,
                height: "100vh"
            }}>
                <Header
                    header="Steam Achievements"
                    themeColor={this.state.themeColor}
                    contentColor={this.state.contentColor}
                    twitchBackgroundColor={this.state.twitchBackgroundColor}
                />
                {this.state.game ?
                    <div id="achievements" style={{
                        overflowY: "auto",
                        height: `calc(100% - ${this.state.headerHeight})`
                    }}>
                        {this.state.game.achievements.map(achievement =>
                            <Achievement
                                achievement={achievement}
                                themeColor={this.state.themeColor}
                                contentColor={this.state.contentColor}
                                twitchBackgroundColor={this.state.twitchBackgroundColor}
                                margin={this.state.margin}
                            />
                        )}
                    </div>
                    :
                    <div id="games" style={{
                        overflowY: "auto",
                        height: `calc(100% - ${this.state.headerHeight})`
                    }}>
                        {this.state.games.map(game =>
                            <Game
                                game={game}
                                themeColor={this.state.themeColor}
                                contentColor={this.state.contentColor}
                                twitchBackgroundColor={this.state.twitchBackgroundColor}
                                margin={this.state.margin}
                            />
                        )}
                    </div>
                }
            </div>
        )
    }
}
