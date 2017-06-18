from enum import Enum

class ACTIONS(Enum):
    ACTION_NIL = 0
    ACTION_UP = 1
    ACTION_LEFT = 2
    ACTION_DOWN = 3
    ACTION_RIGHT = 4
    ACTION_USE = 5
    ACTION_ESCAPE = 6

class WINNER(Enum):
    PLAYER_DISQ = -100
    NO_WINNER = -1
    PLAYER_LOSES = 0
    PLAYER_WINS = 1