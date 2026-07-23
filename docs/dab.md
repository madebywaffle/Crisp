# Dynamic Activation of Brain (DAB)

DAB lowers the AI tick rate of entities the further they are from the
nearest player. A mob right next to a player thinks every tick; the
furthest mobs think once per `max-tick-freq` ticks. Movement, physics,
and collisions are unaffected, so the throttling is not visible in
normal play.

To keep gameplay intact, some entities are never throttled:

- entities that are fighting or were recently hurt
- animals that are breeding
- land mobs that are in water (so they can swim out), unless
  `dont-enable-if-in-water` is turned off

## Configuration (`crisp.yml`)

```yaml
dab:
  enabled: true
  start-distance: 12
  max-tick-freq: 20
  activation-dist-mod: 8
  dont-enable-if-in-water: true
  blacklisted-entities: []
```

| Key | Default | Meaning |
|---|---|---|
| `enabled` | `true` | Master switch. `false` restores Paper behavior exactly. |
| `start-distance` | `12` | Distance from a player (blocks) at which throttling starts. |
| `max-tick-freq` | `20` | AI tick interval for the furthest entities. `20` = once a second. |
| `activation-dist-mod` | `8` | Tick interval formula: `distance² / 2^value`. Lower = throttle harder. |
| `dont-enable-if-in-water` | `true` | Land mobs in water keep full AI. |
| `blacklisted-entities` | `[]` | Entity ids DAB should skip entirely, e.g. `[villager, zombie]`. |

## Notes for mob farms

Villager breeding halls and iron farms far away from players run their
AI less often, which can slow them down. If a farm depends on exact
vanilla timing, add the entity to `blacklisted-entities` or raise
`start-distance`.
