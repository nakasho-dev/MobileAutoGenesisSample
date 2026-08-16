def before_all(context):
    context.default_wait = 10


def after_scenario(context, scenario):
    if scenario.status == "failed":
        pass
