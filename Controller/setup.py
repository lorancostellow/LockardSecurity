# -*- coding: utf-8 -*-

from setuptools import setup, find_packages


with open('README.rst') as f:
    readme = f.read()

with open('LICENSE') as f:
    license = f.read()

setup(
    name='PiCom',
    version='0.0.1',
    description='Lockgard Homeautomation module',
    long_description=readme,
    author='Dylan Coss',
    author_email='dylancoss1@gmail.com',
    url='https://github.com/kennethreitz/samplemod',
    license=license,
    packages=find_packages(exclude=('tests', 'docs'))
)

